package com.kadomos.apigw.response;

/**
 * The class represents the bean holding the Session information
 * returned by the api
 */
public class Session extends Response {

    /**
     * The value represents the session id of the authentication
     */
    private String sessionId;

    public Session() {
        super(true, "");
    }

    public Session(Boolean success, String error, String sessionId) {
        super(success, error);
        this.sessionId = sessionId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
