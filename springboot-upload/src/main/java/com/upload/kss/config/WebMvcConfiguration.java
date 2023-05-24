package com.upload.kss.config;

import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
/**
 * @ClassName WebMvcConfiguration
 * @Description TODO
 * @Author Gientech
 * @Date 2023/5/24 15:40
 * @Version 1.0
 **/
public class WebMvcConfiguration implements WebMvcConfigurer {

    // springboot中springmvc让程序开发者去配置文件上传的额外的静态资源服务的配置
    // 重写addResourceHandlers方法
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 1、指定文件上传的目录
        // 2、指定文件上传的访问路径
        registry.addResourceHandler("/uploadimg").addResourceLocations("file:D://mysoft//temp/");
    }

}
