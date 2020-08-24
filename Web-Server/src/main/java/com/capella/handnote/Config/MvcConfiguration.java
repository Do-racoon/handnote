package com.capella.handnote.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.TimeUnit;

@Configuration
public class MvcConfiguration implements WebMvcConfigurer {
    // templates 폴더 경로에 html 매핑
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")                  // '/'로 시작하는 모든 요청을 다룸
                .addResourceLocations("classpath:/templates/", "classpath:/static/")          // '/templates' 폴더를 지정해서 찾음.
                // addResourceLocations("classpath:/templates/", "classpath:/WEB-INF/resources/")
                .setCacheControl(CacheControl.maxAge(10, TimeUnit.MINUTES));
    }
}