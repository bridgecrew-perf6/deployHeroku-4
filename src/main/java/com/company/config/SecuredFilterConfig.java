package com.company.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecuredFilterConfig {
    @Autowired
    private JwtFilter jwtFilterConfig;

    @Bean
    public FilterRegistrationBean filterRegistrationBean(){
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(jwtFilterConfig);
        bean.addUrlPatterns("/region/adm/*");
        bean.addUrlPatterns("/articleType/adm/*");
        bean.addUrlPatterns("/category/adm/*");
        bean.addUrlPatterns("/profile/adm/*");
        bean.addUrlPatterns("/attach/adm/*");
        bean.addUrlPatterns("/article/adm/*");
        bean.addUrlPatterns("/email/adm/*");
        return bean;
    }
}
