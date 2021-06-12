package com.lzy.miaosha.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.MultipartConfigElement;
import java.util.List;

/**
 * @Description 配置spirngMVC的功能
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    UserArgumentResolvers userArgumentResolvers;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers){
        argumentResolvers.add(userArgumentResolvers);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//映射图片保存地址
        registry.addResourceHandler("/upload/goods/**").addResourceLocations("file:D:\\IdeaProject\\2019\\miaosha_lzy\\src\\main\\resources\\static\\upload\\goods\\");
        registry.addResourceHandler("/upload/head/**").addResourceLocations("file:D:\\IdeaProject\\2019\\miaosha_lzy\\src\\main\\resources\\static\\upload\\head\\");
        registry.addResourceHandler("/upload/people/**").addResourceLocations("file:D:\\IdeaProject\\2019\\miaosha_lzy\\src\\main\\resources\\static\\upload\\people\\");
        registry.addResourceHandler("/upload/**").addResourceLocations("file:D:\\IdeaProject\\2019\\miaosha_lzy\\src\\main\\resources\\static\\upload\\");
        registry.addResourceHandler("/img/**").addResourceLocations("file:D:\\IdeaProject\\2019\\miaosha_lzy\\src\\main\\resources\\static\\img\\");
        registry.addResourceHandler("/img/goods/**").addResourceLocations("file:D:\\IdeaProject\\2019\\miaosha_lzy\\src\\main\\resources\\static\\img\\goods\\");
        registry.addResourceHandler("/lyh/images/**").addResourceLocations("D:\\IdeaProject\\2019\\miaosha_lzy\\src\\main\\resources\\static\\lyh\\images\\");
    }
    @Bean
    MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setLocation("D:\\IdeaProject\\2019\\miaosha_lzy\\src\\main\\resources\\static\\upload");
        return factory.createMultipartConfig();
    }

}
