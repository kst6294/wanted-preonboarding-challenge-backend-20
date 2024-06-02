package com.wanted.demo.global.config;

import com.wanted.demo.global.util.filter.SessionTimeoutFilter;
import jakarta.servlet.FilterRegistration;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<SessionTimeoutFilter> sessionTimeoutFilter() {
        FilterRegistrationBean<SessionTimeoutFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new SessionTimeoutFilter());
        registrationBean.addUrlPatterns("/*");
        registrationBean.setOrder(1); // 필터 순서
        return registrationBean;
    }
}
