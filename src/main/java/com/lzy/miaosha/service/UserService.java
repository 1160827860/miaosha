package com.lzy.miaosha.service;

import com.alibaba.druid.util.StringUtils;
import com.lzy.miaosha.controller.UserController;
import com.lzy.miaosha.dao.UserDao;
import com.lzy.miaosha.domain.User;
import com.lzy.miaosha.exception.GlobalException;
import com.lzy.miaosha.redis.RedisService;
import com.lzy.miaosha.redis.keys.UserKey;
import com.lzy.miaosha.result.CodeMsg;
import com.lzy.miaosha.util.MD5Util;
import com.lzy.miaosha.util.SaltUtil;
import com.lzy.miaosha.util.UUIDUtil;
import com.lzy.miaosha.vo.LoginVo;
import com.lzy.miaosha.vo.RegisterVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Service
public class UserService {
    private static Logger log = LoggerFactory.getLogger(UserService.class);
    public static final String COOKIE_NAME_TOKEN = "token";

    @Autowired
    UserDao userDao;

    @Autowired
    RedisService redisService;

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
    public String login(HttpServletRequest request, HttpServletResponse response, LoginVo loginVo) {
        if(loginVo == null) {
            throw new GlobalException(CodeMsg.ACCESS_FAULT);
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
        //验证密码
        String dbPass = user.getPassword();
        String saltDB = user.getSalt();
        String calcPass = MD5Util.formPassToDBPass(formPass, saltDB);
        if(!calcPass.equals(dbPass)) {
            throw new GlobalException(CodeMsg.PASS_ERROR);
        }
        //生成cookie
        String token = UUIDUtil.uuid();
        addCookie(response, token, user);
        //更新用户登陆次数以及更新用户最后登陆的时间
        updateCount(user);
        log.info(loginVo.toString());
        return token;
    }

    /**
     * 登陆后更新用户的信息
     * @param oldUser 用户未登录的信息
     */
    public void updateCount(User oldUser){
        User user = new User();
        user.setLogincount(oldUser.getLogincount()+1);
        user.setLastLoginDate(new Date());
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
            user.setRegisterdate(new Date());
            user.setPhonenumber(registerVo.getPhone_number());
            user.setNickname(registerVo.getNickName());
            user.setLogincount(0);
            userDao.insertUser(user);
        }
    }
}
