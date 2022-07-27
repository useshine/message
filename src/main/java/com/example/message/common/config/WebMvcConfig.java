package com.example.message.common.config;

import com.example.message.common.interceptor.AuthInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    /** 不需要拦截地址 */
    public static final String[] excludeUrls = {
            "/login"
    };

    public static final String[] excludeSwagger={"/swagger-ui/**","/swagger-resources/**","/v3/**"};
    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        registry.addInterceptor(getHeaderInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(excludeUrls)
                .excludePathPatterns(excludeSwagger)
                .order(-10);
    }

    /**
     * 自定义请求头拦截器
     * @return
     */
    public AuthInterceptor getHeaderInterceptor()
    {
        return new AuthInterceptor();
    }
}
