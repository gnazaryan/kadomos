package com.kadomos.apigw;

import com.kadomos.apigw.interceptors.GatewayInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class KadomosConfiguration implements WebMvcConfigurer {

    @Bean
    public GatewayInterceptor pagePopulationInterceptor() {
        return new GatewayInterceptor();
    }

    public @Override void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(pagePopulationInterceptor());
    }
}
