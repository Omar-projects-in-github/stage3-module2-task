package com.mjc.school.main.config;

import com.mjc.school.main.helper.MenuHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration(proxyBeanMethods = false)
@ComponentScan(basePackages = "com.mjc.school")
@EnableAspectJAutoProxy
public class ApplicationConfig {

    // MenuHelper is created using @Bean annotation by the following reasons:
    // 1. Just to show that beans can be created
    //    not only by @Component, @Service and other stereotype annotations
    //    but also with help of @Bean annotations in configuration classes (marked as @Configuration).
    // 2. To show that bean can be created and initialized programmatically.
    @Bean
    public MenuHelper menuHelper() {
        return new MenuHelper(System.out);
    }
}
