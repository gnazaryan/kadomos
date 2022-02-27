package com.kadomos.apigw.exception;

/**
 * Exception class representing the Kadomos exception
 */
public class KadomosUserFacingException extends KadomosException {

    private String userFacingError;

    public KadomosUserFacingException(String error, String userFacingError) {
        super(error);
        this.userFacingError = userFacingError;
    }

    public String getUserFacingError() {
        return userFacingError;
    }

    public void setUserFacingError(String userFacingError) {
        this.userFacingError = userFacingError;
    }
}
