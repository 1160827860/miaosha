package com.lzy.miaosha.service;

import com.alibaba.druid.sql.visitor.functions.If;
import com.alibaba.druid.util.StringUtils;
import com.lzy.miaosha.controller.UserController;
import com.lzy.miaosha.dao.AddressDao;
import com.lzy.miaosha.dao.GoodsDao;
import com.lzy.miaosha.dao.UserDao;
import com.lzy.miaosha.domain.*;
import com.lzy.miaosha.exception.GlobalException;
import com.lzy.miaosha.redis.RedisService;
import com.lzy.miaosha.redis.keys.BanKey;
import com.lzy.miaosha.redis.keys.UserKey;
import com.lzy.miaosha.result.CodeMsg;
import com.lzy.miaosha.result.Result;
import com.lzy.miaosha.util.MD5Util;
import com.lzy.miaosha.util.SaltUtil;
import com.lzy.miaosha.util.UUIDUtil;
import com.lzy.miaosha.vo.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import sun.reflect.generics.tree.VoidDescriptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserService {
    private static Logger log = LoggerFactory.getLogger(UserService.class);
    public static final String COOKIE_NAME_TOKEN = "token";

    @Autowired
    UserDao userDao;

    @Autowired
    RedisService redisService;

    @Autowired
    AddressDao addressDao;
    @Autowired
    GoodsDao goodsDao;

    /**
     * 通过id返回用户的信息
     * @return 用户的信息
     */
    public User getById(int id){
        //取缓存
        User user = redisService.get(UserKey.getById,""+id,User.class);
        if(user != null){
            return user;
        }
        //取数据库
        user = userDao.getById(id);
        if(user != null){
            redisService.set(UserKey.getById,""+id,user);
        }
        return user;
    }

    /**
     * 通过手机号取用户
     * @param phone 手机号
     * @return 用户信息
     */
    public User getByPhone(String phone){
        //取缓存
        User user = redisService.get(UserKey.getByPh,phone,User.class);
        if(user != null){
            return user;
        }
        //取数据库
        user = userDao.getByPhone(phone);
        if(user != null){
            redisService.set(UserKey.getByPh,""+phone,user);
        }else {
            //防止缓存穿透
            redisService.set(UserKey.getByPhNull,phone,null);
        }
        return user;
    }

    /**
     * 通过token来获取用户信息
     * @param response 为了返回cookie
     * @param token redis中的key的一部分
     * @return 用户信息
     */
    public User getByToken(HttpServletResponse response, String token) {
        if(StringUtils.isEmpty(token)){
            return null;
        }
        User user = redisService.get(UserKey.token,token,User.class);
        //延迟有效期
        if(user != null){
            addCookie(response,token,user);
        }
        return user;
    }
    public String getToken(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        int n = -1;
        for (int i = 0; i < cookies.length; i++) {
            if(cookies[i].getName().equals( "token")){
                n = i;
            }
        }
        return cookies[n].getValue();
    }

    public User getByToken(HttpServletRequest request,HttpServletResponse response) {
        User user = getByToken(response,getToken(request));
        return user;
    }


    /**
     * 向用户的客户端写入token，因为是登陆功能所以一般是没有登陆的情况
     * @param response
     * @param token token的值
     * @param user 用户的信息
     */
    private void addCookie(HttpServletResponse response, String token, User user) {
        redisService.set(UserKey.token, token, user);
        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN, token);
        cookie.setMaxAge(UserKey.token.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    /**
     * 登陆功能
     * @param loginVo 用户发送的数据
     * @return token用户凭证
     */
    @Transactional
    public String login(HttpServletRequest request, HttpServletResponse response, @Valid LoginVo loginVo) {
        if(loginVo == null) {
            throw new GlobalException(CodeMsg.ACCESS_FAULT);
        }
        if (!checkUserAuthority(loginVo.getPhone_number())){
            throw new GlobalException(CodeMsg.ACCOUNT_BANDED);
        }
        //检查验证码是否正确
        String Session_Check = (String) request.getSession().getAttribute("CHECK_CODE");
        if(!Session_Check.toLowerCase().equals(loginVo.getCheckCode().toLowerCase())){
            throw new GlobalException(CodeMsg.CHECKCODE_ERROR);
        }
        String phone = loginVo.getPhone_number();
        String formPass = loginVo.getPassWords();
        //判断手机号是否存在
        User user = getByPhone(phone);
        if(user == null) {
            throw new GlobalException(CodeMsg.ACCOUNT_NOT_EXIST);
        }
//        user = getByToken(request,response);
        //验证密码
        String dbPass = user.getPassword();
        String saltDB = user.getSalt();
        String calcPass = MD5Util.formPassToDBPass(formPass, saltDB);
        if(!calcPass.equals(dbPass)) {
            throw new GlobalException(CodeMsg.PASS_ERROR);
        }
        if(user.getAuthority().equals("3")){
            throw new GlobalException(CodeMsg.ACCOUNT_BANDED);
        }
        //生成cookie
        String token = UUIDUtil.uuid();
        addCookie(response, token, user);
        //更新用户登陆次数以及更新用户最后登陆的时间
        updateCount(user);
        log.info(loginVo.toString());
        //管理员跳转管理界面
        if (user.getAuthority().equals("0")){
            return "root";
        }
        return token;
    }

    /**
     * 登陆后更新用户的信息
     * @param oldUser 用户未登录的信息
     */
    public void updateCount(User oldUser){
        User user = new User();
        user.setLogincount(oldUser.getLogincount()+1);
        user.setLastLoginDate(new java.sql.Date(new java.util.Date().getTime()));
        user.setPhonenumber(oldUser.getPhonenumber());
        userDao.updateUser(user);
        redisService.set(UserKey.getByPh,user.getPhonenumber(),user);
    }
    /**
     * 注册用户
     * @param registerVo 用户信息
     * @return
     */
    public void register(RegisterVo registerVo){
        //先取缓冲查看手机号是否已经被注册
        User user = redisService.get(UserKey.getByPh,""+registerVo.getPhone_number(),User.class);
        if(user != null){
            throw new GlobalException(CodeMsg.ACCOUNT_EXIST);
        }
        //如果缓冲没有就检查数据库
        user = userDao.getByPhone(registerVo.getPhone_number());
        if(user != null){
            throw new GlobalException(CodeMsg.ACCOUNT_EXIST);
        }else {//将用户信息插入数据库
            user = new User();
            String salt = SaltUtil.getSalt();
            String dBPass = MD5Util.inputPassToDbPass(registerVo.getPassWords(),salt);
            user.setSalt(salt);
            user.setPassword(dBPass);
            user.setRegisterdate(new java.sql.Date(new java.util.Date().getTime()));
            user.setPhonenumber(registerVo.getPhone_number());
            user.setNickname(registerVo.getNickName());
            user.setLogincount(0);
            user.setAuthority("1");
            userDao.insertUser(user);
        }
    }

    /**
     * 更新头像
     * @param filePath 新头像的地址
     */
    public User updateHead(HttpServletRequest request,HttpServletResponse response,String phone,String filePath,User oldUser){
        oldUser.setHead(filePath);
        oldUser.setPhonenumber(phone);
        userDao.updateUser(oldUser);
        redisService.delete(UserKey.token,getToken(request));
        redisService.set(UserKey.token,getToken(request),oldUser);
        return oldUser;
    }

    /**
     * 删除缓存
     * @param request
     * @param response
     */
    public void delRedisUser(HttpServletRequest request,HttpServletResponse response){
        User user = getByToken(request,response);
        String ph = user.getPhonenumber();
        redisService.delete(UserKey.token,getToken(request));
        redisService.delete(UserKey.getByPh,ph);
    }
    /**
     * 修改密码
     * 0代表失败，1代表成功
     */
     public int changePasswords(HttpServletRequest request,User user, ChangePasswordsVo changePasswordsVo){
         if(!user.getPassword().equals(MD5Util.inputPassToDbPass(changePasswordsVo.getOldPasswords(),user.getSalt()))){
             return 0;
         }else {
             user.setPassword(MD5Util.inputPassToDbPass(changePasswordsVo.getNewPasswords(),user.getSalt()));
             userDao.updateUser(user);
             redisService.delete(UserKey.token,getToken(request));
             redisService.delete(UserKey.getByPh,user.getPhonenumber());
             return 1;
         }
     }

    /**
     * 添加收货地址
     */
    public void addAddres(HttpServletRequest request, HttpServletResponse response, AddressVo addressVo){
        User user = getByToken(request,response);
        Address address = new Address();
        address.setUser_id(user.getId());
        address.setName(addressVo.getName());
        address.setPhonenumber(addressVo.getPhone());
        address.setUser_address(addressVo.getAddress());
        addressDao.insertAddress(address);
     }
    public List<AddressVo> getAddressByUserId(HttpServletRequest request,HttpServletResponse response){
        List<AddressVo> res = new ArrayList<>();
        int id = getByToken(request,response).getId();
        res = addressDao.getByUserId(id);
        return res;
    }

    public List<AddressVo> getAddressById(Integer id){
        return  addressDao.getByUserId(id);
    }


    public List<User> getAllUser(){
        List<User> res = new ArrayList<>();
        res = userDao.getAllUser();
        for (int i = 0; i < res.size(); i++) {
            User user = res.get(i);
            String authority = user.getAuthority();
            if (authority.equals("1")) {
                user.setAuthority("用户");
            }else if(authority.equals("2")){
                user.setAuthority("商家");
            }else if (authority.equals("3")){
                user.setAuthority("被封号");
            }else {
                user.setAuthority("管理员");
            }
        }
        return res;
    }


    public void updateAddress(AddressVo addressVo){
        Address address = new Address();
        address.setId(addressVo.getId());
        address.setUser_address(addressVo.getAddress());
        address.setPhonenumber(addressVo.getPhone());
        address.setName(addressVo.getName());
        addressDao.updateAddress(address);
    }

    public void applyShop(HttpServletRequest request,HttpServletResponse response,ShopVo shopVo){
        Shop shop = new Shop();
        int userId = getByToken(request,response).getId();
        shop.setAuthority("1");
        shop.setUserId(userId);
        shop.setInfo(shopVo.getInfo());
        shop.setName(shopVo.getName());
        shop.setPeopleName(shopVo.getPeopleName());
        shop.setPeopleNum(shopVo.getPeopleNum());
        userDao.applyShop(shop);
    }

    /**
     * 上传图片并且返回文件名
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    public String updatePic(HttpServletRequest request,HttpServletResponse response) throws IOException {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("file"); //获取单个文件
        if (file == null){
            throw new GlobalException(CodeMsg.UPDATE_EMPTY);
        }
        //使用的绝对路径未改
        String originalFileName = file.getOriginalFilename();
        String suffixName = originalFileName.substring(originalFileName.lastIndexOf("."));
        String fileName = UUIDUtil.uuid()+suffixName;
        File newFile=new File("D:\\IdeaProject\\2019\\miaosha_lzy\\src\\main\\resources\\static\\upload\\people\\"+fileName);
        file.transferTo(newFile);
        return fileName;
    }

    /**
     * 提交身份证照片
     * @param user
     * @param path
     * @param c
     */
    public void updatePeoplePic(User user,String path,int c){
        Shop shop = new Shop();
        shop.setUserId(user.getId());
        if(c == 0){
            shop.setFrontPic(path);
        }else {
            shop.setBackPic(path);
        }
        userDao.updateShop(shop);
    }

    /**
     * 检查用户权限，是否被封号
     * @param phone 电话
     * @return
     */
    public boolean checkUserAuthority(String phone){
        String info =  redisService.get(BanKey.banedUser,""+phone,String.class);
        if(info == null){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 获取店家的权限
     * @param id
     * @return
     */
    public String getShopStatus(int id){
        Shop shop = userDao.getShopByUserId(id);
        if(shop == null){
            return "0";
        }else {
            return  userDao.getShopByUserId(id).getAuthority();
        }
    }

    public String updateGoodsPic(HttpServletRequest request,HttpServletResponse response) throws IOException {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("file"); //获取单个文件
        if (file == null){
            throw new GlobalException(CodeMsg.UPDATE_EMPTY);
        }
        //使用的绝对路径未改
        String originalFileName = file.getOriginalFilename();
        String suffixName = originalFileName.substring(originalFileName.lastIndexOf("."));
        String fileName = UUIDUtil.uuid()+suffixName;
        File newFile=new File("D:\\IdeaProject\\2019\\miaosha_lzy\\src\\main\\resources\\static\\upload\\goods\\"+fileName);
        file.transferTo(newFile);
        return fileName;
    }
    /**
     * 提交商品的图片
     * @param user
     * @param path
     * @param name
     */
    public void updateGoodsPath(User user,String path,String name){
        Shop shop = userDao.getShopByUserId(user.getId());
        Goods goods = new Goods();
        goods.setShopId(shop.getId());
        goods.setGoodsImg("/upload/goods/"+path);
        goods.setGoodsName(name);
        goodsDao.updateGoodsPic(goods);
    }

    public void delGoods(int id){
        Goods goods = new Goods();
        goods.setStatus(1);
        goodsDao.updateGoods(goods);
    }


    public void insertComplaint(ComplaintVo complaintVo) {
        Complaint complaint = new Complaint();
        complaint.setOrderId(complaintVo.getOrderId());
        complaint.setContent(complaintVo.getContent());
        complaint.setComplaintId(complaintVo.getComplaintId());
        complaint.setApplyDate(new java.sql.Date(new java.util.Date().getTime()));
        complaint.setPhone(complaintVo.getPhone());
        userDao.insertComplaint(complaint);
    }
}
