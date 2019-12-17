package com.lc.studentmanager.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @BelongsProject: studentmanager
 * @BelongsPackage: com.lc.studentmanager.config
 * @Author: lc
 * @CreateTime: 2019-12-12 19:30
 * @Description:
 */
@Configuration
@MapperScan(basePackages = "com.lc.studentmanager.dao.mapper")
public class MybatisConfig {

}
