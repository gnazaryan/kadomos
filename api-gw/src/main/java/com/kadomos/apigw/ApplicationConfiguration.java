package com.kadomos.apigw;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@Configuration
@PropertySource("classpath:application.properties")
public class ApplicationConfiguration implements EnvironmentAware {

    @Autowired
    private Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public <T> T getProperty(String key, Class<T> targetType) {
        return this.environment.getProperty(key, targetType);
    }
}
