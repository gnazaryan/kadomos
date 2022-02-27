package com.kadomos.apigw.interceptors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.stream.Collectors;

import static com.kadomos.apigw.constants.Constants.REQUEST_LOG_FORMAT;

public class GatewayInterceptor implements HandlerInterceptor {

    private static final Logger logger = LogManager.getLogger(GatewayInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //log all request information to file on info level
        String requestParameters = request.getParameterMap().entrySet().stream().map(item-> item.getKey() + " - " + String.join(",",  item.getValue())).collect(Collectors.joining(","));
        logger.info(String.format(REQUEST_LOG_FORMAT, request.getRequestURL(), requestParameters));
        return true;
    }
}
