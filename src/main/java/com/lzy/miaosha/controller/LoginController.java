package com.lzy.miaosha.controller;


import com.lzy.miaosha.redis.RedisService;
import com.lzy.miaosha.util.Base64Util;
import com.lzy.miaosha.util.BaseValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;

/**
 * @Description 用户登陆模块控制层
 * @author 李正阳
 * @date 2020/2/26
 * @version 0.1
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    RedisService redisService;

    /**
     * 获取验证码
     * @return base64的
     */
    @RequestMapping("/getCheckCode")
    @ResponseBody
    public String getChcekCode(HttpServletRequest request){
        BaseValidate validate = Base64Util.getRandomCode();
        request.getSession().setAttribute("CHECK_CODE",validate.getValue());
        return "data:image/png;base64,"+validate.getBase64Str();
    }


}
