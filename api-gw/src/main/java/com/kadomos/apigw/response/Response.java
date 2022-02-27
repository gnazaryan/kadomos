package com.kadomos.apigw.response;

/**
 * The response object representing the base response result
 */
public class Response {

    /**
     * value representing if the response is successful
     */
    private Boolean success;

    /**
     * value representing any error faceing to user
     */
    private String error;

    public Response(Boolean success, String error) {
        this.success = success;
        this.error = error;
    }

    public Response() {
        this(true, "");
    }

    public Boolean isSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
