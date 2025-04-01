package com.example.schedulerproject_jpa.config;

import com.example.schedulerproject_jpa.filter.LoginCheckFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<LoginCheckFilter> loginCheckFilter(){
        FilterRegistrationBean<LoginCheckFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new LoginCheckFilter());
        registrationBean.addUrlPatterns("/api/*"); // 모든 API 경로에 적용
        registrationBean.setOrder(1); // 우선순위 설정
        return registrationBean;
    }
}
