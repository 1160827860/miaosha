package com.lzy.miaosha.controller;


import com.lzy.miaosha.redis.RedisService;
import com.lzy.miaosha.result.CodeMsg;
import com.lzy.miaosha.result.Result;
import com.lzy.miaosha.service.UserService;
import com.lzy.miaosha.util.Base64Util;
import com.lzy.miaosha.util.BaseValidate;
import com.lzy.miaosha.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

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

    @Autowired
    UserService userService;

    /**
     * 获取验证码
     * @return base64的
     */
    @RequestMapping("/getCheckCode")
    @ResponseBody
    public String getChcekCode(HttpServletRequest request){
        BaseValidate validate = Base64Util.getRandomCode();
        System.out.println(validate.getValue());
        request.getSession().setAttribute("CHECK_CODE",validate.getValue());
        return "data:image/png;base64,"+validate.getBase64Str();
    }

    /**
     * 登陆功能
     * @param loginVo 用户传输的信息
     * @return 是否成功的数据
     */
    @RequestMapping(value = "/do_login")
    @ResponseBody
    public Result<String> doLogin(HttpServletRequest request, HttpServletResponse response, @Valid LoginVo loginVo) {
        String token = userService.login(request,response,loginVo);
        if(token.equals("root")){
            return Result.error(CodeMsg.MANGER_USER);
        }
        return Result.success(token);
    }


}
