package com.kadomos.apigw.exception;

/**
 * Exception class representing the Kadomos exception
 */
public class KadomosRuntimeException extends RuntimeException {

    public KadomosRuntimeException(String error) {
        super(error);
    }
}
