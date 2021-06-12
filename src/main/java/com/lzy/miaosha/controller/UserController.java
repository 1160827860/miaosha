package com.lzy.miaosha.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lzy.miaosha.domain.Address;
import com.lzy.miaosha.domain.Goods;
import com.lzy.miaosha.domain.Shop;
import com.lzy.miaosha.domain.User;
import com.lzy.miaosha.exception.GlobalException;
import com.lzy.miaosha.redis.RedisService;
import com.lzy.miaosha.redis.keys.UserKey;
import com.lzy.miaosha.result.CodeMsg;
import com.lzy.miaosha.result.Result;
import com.lzy.miaosha.service.GoodsService;
import com.lzy.miaosha.service.RootService;
import com.lzy.miaosha.service.ShopService;
import com.lzy.miaosha.service.UserService;
import com.lzy.miaosha.util.UUIDUtil;
import com.lzy.miaosha.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description 用户管理模块控制层
 * @author 李正阳
 * @date 2020/2/26
 * @version 0.1
 */
@Controller
@RequestMapping("/user")
@Slf4j
public class UserController {

    private static Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;

    @Autowired
    RedisService redisService;

    @Autowired
    RootService rootService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    ShopService shopService;

    /**
     * 返回登陆界面
     * @return
     */
    @RequestMapping("/to_login")
    public String toLogin() {
        return "login";
    }

    /**
     * 修改密码
     * @return
     */
    @RequestMapping("/change_password")
    @ResponseBody
    public Result<CodeMsg> changePass(HttpServletRequest request,HttpServletResponse response,@Valid ChangePasswordsVo changePasswordsVo){
        if(changePasswordsVo == null) {
            throw new GlobalException(CodeMsg.ACCESS_FAULT);
        }
        if(changePasswordsVo.getNewPasswords().equals(changePasswordsVo.getOldPasswords())){
            return  Result.error(CodeMsg.ACCESS_FAULT);
        }else if (!changePasswordsVo.getNewPasswords().equals(changePasswordsVo.getCheckPassWords())){
            return Result.error(CodeMsg.ACCESS_FAULT);
        }else {
            User user = userService.getByToken(request,response);
            int mark = userService.changePasswords(request,user,changePasswordsVo);
            if(mark == 0){
                return Result.error(CodeMsg.ACCESS_FAULT);
            }else {
                return Result.success(CodeMsg.SUCCESS);
            }
        }
    }

    @RequestMapping("/update")
    @ResponseBody
    public Result<User> updateHead(HttpServletRequest request,HttpServletResponse response) throws IOException {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = multipartRequest.getFile("file"); //获取单个文件
        //使用的绝对路径未改
        String originalFileName = file.getOriginalFilename();
        String suffixName = originalFileName.substring(originalFileName.lastIndexOf("."));
        String fileName = UUIDUtil.uuid()+suffixName;
        File newFile=new File("D:\\IdeaProject\\2019\\miaosha_lzy\\src\\main\\resources\\static\\upload\\head\\"+fileName);
        file.transferTo(newFile);
        User user = userService.getByToken(request,response);
        user =userService.updateHead(request,response,user.getPhonenumber(),"upload/head/"+fileName,user);
        return Result.success(user);
    }

    /**
     * 获取用户的全部信息在个人中心展示
     * 先从redis匹配用户发来的cookie
     * @return
     */
    @RequestMapping("/getInfo")
    @ResponseBody
    public Result<User> getUserInfo(HttpServletRequest request,HttpServletResponse response){
        User user = userService.getByToken(request,response);
        return Result.success(user);
    }

    /**
     * 退出账号
     * @param request
     * @param response
     * @return 返回登录界面
     */
    @RequestMapping("/exit")
    public String exit(HttpServletRequest request,HttpServletResponse response){
        userService.delRedisUser(request,response);
        return "login";
    }



    @RequestMapping(value = "/register")
    @ResponseBody
    public Result<CodeMsg> register(@Valid  RegisterVo registerVo){
        userService.register(registerVo);
        return Result.success(CodeMsg.SUCCESS);
    }

    /**
     * 检查用户是否登录
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/check_login")
    @ResponseBody
    public Result<CodeMsg> checkLogin(HttpServletRequest request,HttpServletResponse response){
        User user = userService.getByToken(request,response);
        if(user != null){
            //用户登录有效
            return Result.success(CodeMsg.SUCCESS);
        }else {
            //用户未登录
            return Result.error(CodeMsg.NEED_LOGIN);
        }
    }
    /**
     * 添加用户地址
     * @param request
     * @param response
     * @return 地址的List
     */
    @RequestMapping(value = "/address")
    @ResponseBody
    public Result<List<AddressVo>> getAddress(HttpServletRequest request,HttpServletResponse response){
        return Result.success(userService.getAddressByUserId(request,response));
    }

    /**
     * 添加用户地址
     * @param request
     * @param response
     * @param addressVo 地址交互对象
     * @return
     */
    @RequestMapping(value = "/add_address")
    @ResponseBody
    public Result<CodeMsg> addAddress(HttpServletRequest request,HttpServletResponse response,@Valid AddressVo addressVo){
        userService.addAddres(request,response,addressVo);
        return Result.success(CodeMsg.SUCCESS);
    }

    /**
     * 修改收货地址
     * @param addressVo
     * @return
     */
    @RequestMapping(value = "/update_address")
    @ResponseBody
    public Result<CodeMsg> updateAddress(@Valid AddressVo addressVo){
        userService.updateAddress(addressVo);
        return Result.success(CodeMsg.SUCCESS);
    }

    @RequestMapping(value = "/apply_shop")
    @ResponseBody
    public Result<CodeMsg> applyShop(HttpServletRequest request,HttpServletResponse response,@Valid ShopVo shopVo){
        userService.applyShop(request,response,shopVo);
        return Result.success(CodeMsg.SUCCESS);
    }

    @RequestMapping("/update_front")
    @ResponseBody
    public Result<CodeMsg> updatePeopleFrontPic(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String fileName = userService.updatePic(request,response);
        User user = userService.getByToken(request,response);
        userService.updatePeoplePic(user,"upload/people/"+fileName,0);
        return Result.success(CodeMsg.SUCCESS);
    }
    @RequestMapping("/update_back")
    @ResponseBody
    public Result<CodeMsg> updatePeopleBackPic(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String fileName = userService.updatePic(request,response);
        User user = userService.getByToken(request,response);
        userService.updatePeoplePic(user,"upload/people/"+fileName,1);
        return Result.success(CodeMsg.SUCCESS);
    }

    /**
     * 检查用户是否具有开店资格
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping("/status")
    @ResponseBody
    public Result<CodeMsg> checkUserStatus(HttpServletRequest request,HttpServletResponse response)  {
        User user = userService.getByToken(request,response);
        String status = userService.getShopStatus(user.getId());
        if(status.equals("1")){
            //正在审核
            return Result.success(CodeMsg.UNAUDITED_USER);
        }else if(status.equals("2")){
            //认证
            return Result.success(CodeMsg.CERTIFIED_USER);
        } else if(status.equals("3")){
            //未申请
            return Result.success(CodeMsg.UNSUPPLIED_USER);
        }
        else {
            //封禁
            return Result.success(CodeMsg.BANED_USER);
        }
    }

    @RequestMapping("/updateGoodsImg")
    @ResponseBody
    public Result<CodeMsg> updateGoodsImg(HttpServletRequest request,HttpServletResponse response, @RequestParam(required = false,value = "params")String params) throws IOException {
        Goods goods = JSON.parseObject(params,Goods.class);
        String goodName =  goods.getGoodsName();
        User user = userService.getByToken(request,response);
        goods.setStatus(0);
        goodsService.insertGoods(goods,user);
        String fileName = userService.updateGoodsPic(request,response);
        userService.updateGoodsPath(user,fileName,goodName);
        return Result.success(CodeMsg.SUCCESS);
    }
    @RequestMapping("/show_my_goods")
    @ResponseBody
    public Result<List<Goods>> showMyGoods(HttpServletRequest request, HttpServletResponse response) {
        User user = userService.getByToken(request,response);
        Shop shop = shopService.getByUserId(user);
        return Result.success(goodsService.showMyGoods(shop));
    }

    @RequestMapping("/show_del_goods")
    @ResponseBody
    public Result<List<Goods>> showMyDelGoods(HttpServletRequest request, HttpServletResponse response) {
        User user = userService.getByToken(request,response);
        Shop shop = shopService.getByUserId(user);
        return Result.success(goodsService.showMyDelGoods(shop));
    }

    @RequestMapping(value = "/update_goods")
    @ResponseBody
    public Result<CodeMsg> update(@Valid Goods goods){
        goodsService.updateGoods(goods);
        return Result.success(CodeMsg.SUCCESS);
    }

    @RequestMapping(value = "/delGoods")
    @ResponseBody
    public Result<CodeMsg> banedShop(@RequestBody JSONArray data){
        String good = data.toString();
        List<Goods> goods = new ArrayList<>(JSONArray.parseArray(good,Goods.class));
        int id = goods.get(0).getId();
        userService.delGoods(id);
        return Result.success(CodeMsg.SUCCESS);
    }

    @RequestMapping(value = "/complaint")
    @ResponseBody
    public Result<CodeMsg> complaint(@Valid ComplaintVo complaintVo){
        userService.insertComplaint(complaintVo);
        goodsService.changeGoodsStaus(7,complaintVo.getOrderId());
        return Result.success(CodeMsg.SUCCESS);
    }



}
