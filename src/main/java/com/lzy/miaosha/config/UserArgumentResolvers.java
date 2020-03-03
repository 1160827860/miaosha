package com.lzy.miaosha.config;

import com.alibaba.druid.util.StringUtils;
import com.lzy.miaosha.domain.User;
import com.lzy.miaosha.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description 自定义参数解析解析自定义的时间格式、自定义解析Map对象等这些spring原本不支持的对象格式
 * @author 李正阳
 * @date 2020/2/26
 * @version 0.1
 */
@Service
public class UserArgumentResolvers implements HandlerMethodArgumentResolver {

    @Autowired
    UserService userService;

    /**
     * 检查类对象是否等于user.class
     * @param methodParameter
     * @return true 调用resolveArgument
     */
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        Class<?> clazz = methodParameter.getParameterType();
        return clazz == User.class;
    }

    /**
     *检查用户发送的cookie，并且从redis中获取存储的用户信息，这也是为什么GoodController的获取商品详情，没有传入用户信息也能通
     * 过cookie从缓存中取到。
     */
    @Override
    public Object resolveArgument(MethodParameter methodParameter,
                                  ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest,
                                  WebDataBinderFactory webDataBinderFactory) throws Exception {

        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        HttpServletResponse response = nativeWebRequest.getNativeResponse(HttpServletResponse.class);
        //检查手机端和电脑端获取token是否为空
        String paramToken = request.getParameter(UserService.COOKIE_NAME_TOKEN);
        String cookieToken = getCookieValue(request,UserService.COOKIE_NAME_TOKEN);
        if(StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)){
            return null;
        }
        //优先获取手机端的token
        String token = StringUtils.isEmpty(paramToken)?cookieToken:paramToken;
        //返回获取的用户信息
        return userService.getByToken(response,token);
    }

    /**
     * 从电脑端发送的所有cookie中找到目标cookie
     * @param request 内置对象
     * @param cookieNameToken cookie的名字
     * @return cookie的值
     */
    private String getCookieValue(HttpServletRequest request, String cookieNameToken) {
        Cookie[] cookies = request.getCookies();
        if(cookies == null || cookies.length <= 0){
            return null;
        }
        for(Cookie cookie:cookies){
            if(cookie.getName().equals(cookieNameToken)){
                return cookie.getValue();
            }
        }
        return null;
    }
}
