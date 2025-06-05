package com.example.example_auth.config;

import com.example.example_auth.filter.BasicAuthenticationFilter;
import com.example.example_auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FilterConfig {

    private final UserRepository userRepository;

    @Bean
    FilterRegistrationBean<BasicAuthenticationFilter> userAuthenticationBean() {
        FilterRegistrationBean<BasicAuthenticationFilter> filerFilterRegistrationBean = new FilterRegistrationBean<>();
        filerFilterRegistrationBean.setFilter(new BasicAuthenticationFilter(userRepository));
        filerFilterRegistrationBean.addUrlPatterns("/api/private/*");
        filerFilterRegistrationBean.setOrder(1);
        return filerFilterRegistrationBean;
    }
}
