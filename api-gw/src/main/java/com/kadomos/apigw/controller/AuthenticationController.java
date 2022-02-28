package com.kadomos.apigw.controller;

import com.kadomos.apigw.business.AuthenticationService;
import com.kadomos.apigw.response.Session;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "authentication/")
public class AuthenticationController {

    private static final Logger logger = LogManager.getLogger(AuthenticationController.class);

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("authenticate")
	public Session authenticate(@RequestParam(name = "username", required = true) String username,
                               @RequestParam(name = "password", required = true) String password) {
        Session result = new Session();
        try {
            String sessionId = authenticationService.authenticate(username, password);
            result.setSessionId(sessionId);
        } catch (Exception ex) {
            logger.error("Unable to authenticate a user with error: " + ex.getMessage());
            result.setSuccess(false);
            result.setError("The username or password supplied is not matching, please correct and try again");
        }
		return result;
	}
}
