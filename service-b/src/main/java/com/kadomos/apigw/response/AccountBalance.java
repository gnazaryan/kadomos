package com.kadomos.apigw.response;

/**
 * The class represents the bean holding the balance information
 * returned by the api
 */
public class AccountBalance extends Response {

    /**
     * The value represents the balance of the account
     */
    private Float value;

    public AccountBalance() {
        super(true, "");
    }

    public AccountBalance(Boolean success, String error, Float value) {
        super(success, error);
        this.value = value;
    }

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }
}
