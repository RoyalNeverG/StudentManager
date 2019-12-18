package com.lc.studentmanager.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

/**
 * @BelongsProject: wechart_lc
 * @BelongsPackage: com.test.wechart.config
 * @Author: 42119
 * @CreateTime: 2019-11-18 23:27
 * @Description:
 */
@Configuration
@EnableSwagger2
//使用环境 是否启用swagger配置
//@Profile({"api"})
public class SwaggerConfig {

    /**
     * 是否开启swagger配置  默认为false
     */
    @Value("${swagger.enable:false}")
    private boolean enable;

    /**
     * 构建api
     * @return
     */
    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .enable(enable)
                .select()
                //如果不想将所有的接口都通过swagger管理的话，可以将RequestHandlerSelectors.any()修改为RequestHandlerSelectors.basePackage()
                .apis(RequestHandlerSelectors.basePackage("com.lc.studentmanager.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * api信息
     * @return
     */
    @Bean
    public ApiInfo apiInfo(){
        return new ApiInfo("学生选课管理接口", "", "1.0.0", "",  new Contact("娄晨", "www.louchen.top", "421192425@qq.com"), "", "", new ArrayList<>());
    }

}
