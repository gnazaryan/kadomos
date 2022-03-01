package com.kadomos.apigw.interceptors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kadomos.apigw.ApplicationConfiguration;
import com.kadomos.apigw.business.AuthenticationService;
import com.kadomos.apigw.constants.Constants;
import com.kadomos.apigw.response.Response;
import com.kadomos.apigw.util.Convenience;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.stream.Collectors;

import static com.kadomos.apigw.constants.Constants.REQUEST_LOG_FORMAT;

@Component
public class GatewayInterceptor implements HandlerInterceptor {

    private static final Logger logger = LogManager.getLogger(GatewayInterceptor.class);

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private ApplicationConfiguration applicationConfiguration;

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //log all request information to file on info level
        String requestParameters = request.getParameterMap().entrySet().stream().map(item-> item.getKey() + " - " + String.join(",",  item.getValue())).collect(Collectors.joining(","));
        logger.info(String.format(REQUEST_LOG_FORMAT, request.getRequestURL(), requestParameters));

        /**
         * Allow users to access the authentication/authenticate api without any authentication
         */
        if (applicationConfiguration.getProperty(Constants.ENVIRONMENT.AUTHENTICATION_ENABLED, Boolean.class) &&
                !request.getRequestURL().toString().contains("authentication/authenticate") &&
                !request.getRequestURL().toString().equals("http://localhost/")) {
            String sessionId[] = request.getParameterMap().get(Constants.SESSION_ID);
            if (sessionId.length > 0 && Convenience.hasValue(sessionId[0])) {
                Boolean authenticated = false;
                try {
                    authenticated = authenticationService.isAuthenticated(sessionId[0]);
                } catch(Exception ex) {
                    logger.error("Unable to authenticate the user, interrupting the request");
                }
                if (!authenticated) {
                    Response unauthenticatedResponse = new Response();
                    unauthenticatedResponse.setSuccess(false);
                    unauthenticatedResponse.setError("The session is not authenticated, please run authentication first");
                    response.getWriter().write(mapper.writeValueAsString(unauthenticatedResponse));
                }
                return authenticated;
            }
        }
        return true;
    }
}
