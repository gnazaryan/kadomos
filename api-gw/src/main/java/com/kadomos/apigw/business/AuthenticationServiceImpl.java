package com.kadomos.apigw.business;

import com.kadomos.apigw.exception.KadomosException;
import com.kadomos.apigw.util.EncryptionUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class AuthenticationServiceImpl implements AuthenticationService{

    /**
     * THe cache holds a mapping of the sessionid to the time the session was authenticated
     */
    private static Map<String, Long> authenticationCache = new HashMap<>();

    /**
     * The below username and password is hardcoded
     * but it is supposed to be stored in the database
     * based on the user registration
     */
    private static String USERNAME = "root";
    private static String PASSWORD = "Y77rH0Yxgfb2gWnqu+9l8Q==:8RfkALc0N7hjk+TgQPni9Q==";//Password is kadomos

    /**
     * This is a predefined private key that should be kept securely
     * to be used by the private/public key cryptography Encryption
     */
    private static String SECURITY_KEY = "SECURITY_KEY";

    /**
     * The duration of the authentication session, after 10 minutes of inactivity, the session will
     * time out and user not authenticated
     */
    private static Integer AUTHENTICATION_DURATION = 1000 * 60 * 10; // 10 minutes

    @Override
    public String authenticate(String username, String password) throws KadomosException {
            if (USERNAME.equals(username)
            && password.equals(EncryptionUtil.decrypt(PASSWORD, SECURITY_KEY))) {
                UUID uuID = UUID.randomUUID();
                authenticationCache.put(uuID.toString(), System.currentTimeMillis());
                return uuID.toString();
            } else {
                throw new KadomosException("The username or password supplied is not matching, please correct and try again");
            }
    }

    @Override
    public Boolean isAuthenticated(String sessionId) throws KadomosException {
        if (authenticationCache.containsKey(sessionId)) {
            Long authenticationTime = authenticationCache.get(sessionId);
            if (authenticationTime != null) {
                Long now = System.currentTimeMillis();
                if (now - authenticationTime > AUTHENTICATION_DURATION) {
                    authenticationCache.remove(sessionId);
                    return false;
                } else {
                    authenticationCache.put(sessionId, now);
                    return true;
                }
            }
        }
        return false;
    }
}
