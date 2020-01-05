package com.lc.studentmanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @BelongsProject: wechart_lc
 * @BelongsPackage: com.test.wechart.config
 * @Author: 42119
 * @CreateTime: 2019-11-18 23:45
 * @Description:
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {

    /**
     * swagger ui资源映射
     * @param registry
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
    }

    /**
     * 重定向页面
     * 使用/api-docs 代替/swagger-ut.html
     * @param registry
     */
    @Override
    protected void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/manager-api","/swagger-ui.html");
    }

    /**
     * 响应的http请求消息信息 编码设置
     * @return
     */
    @Bean
    public HttpMessageConverter<String> responseBodyStringConverter(){
        StringHttpMessageConverter stringHttpMessageConverter=new StringHttpMessageConverter(StandardCharsets.UTF_8);
        return stringHttpMessageConverter;
    }

    /**
     * 加入到配置
     * @param converters
     */
    @Override
    protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(responseBodyStringConverter());
    }
}
