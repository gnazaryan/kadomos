package com.kadomos.apigw.business;


import com.kadomos.apigw.exception.KadomosException;

/**
 * The service is resposnable for authenticating the identity of the users
 */
public interface AuthenticationService {

    /**
     * The api does authenticate the username and password and returns a session id representing the
     * authenticated user
     * @param username the username of the requesting user
     * @param password the password of the requesting user
     * @return
     * @throws KadomosException
     */
    String authenticate(String username, String password) throws KadomosException;

    /**
     * The api checks if the provided session id is authenticated and
     * the authentication is not expired
     * @param sessionId the session identifying the authentication
     */
    Boolean isAuthenticated(String sessionId) throws KadomosException;
}
