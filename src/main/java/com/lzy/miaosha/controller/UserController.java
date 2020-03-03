package com.lzy.miaosha.controller;

import com.lzy.miaosha.result.CodeMsg;
import com.lzy.miaosha.result.Result;
import com.lzy.miaosha.service.UserService;
import com.lzy.miaosha.vo.LoginVo;
import com.lzy.miaosha.vo.RegisterVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @Description 用户管理模块控制层
 * @author 李正阳
 * @date 2020/2/26
 * @version 0.1
 */
@Controller
@RequestMapping("/user")
public class UserController {

    private static Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;

    /**
     * 返回登陆界面
     * @return
     */
    @RequestMapping("/to_login")
    public String toLogin() {
        return "login";
    }

    /**
     * 登陆功能
     * @param loginVo 用户传输的信息
     * @return 是否成功的数据
     */
    @RequestMapping(value = "/do_login")
    @ResponseBody
    public Result<String> doLogin(HttpServletRequest request,HttpServletResponse response, @Valid LoginVo loginVo) {
        String token = userService.login(request,response,loginVo);
        return Result.success(token);
    }

    @RequestMapping(value = "/register")
    @ResponseBody
    public Result<CodeMsg> register(@Valid  RegisterVo registerVo){
        userService.register(registerVo);
        return Result.success(CodeMsg.SUCCESS);
    }

}
