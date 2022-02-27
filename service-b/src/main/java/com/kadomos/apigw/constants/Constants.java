package com.kadomos.apigw.constants;

public class Constants {

    public static final String REQUEST_LOG_FORMAT = "URL: %s, PARAMETERS: %s";

    public interface KADOMOS_SERVICES {
        String SERVICE_A = "SERVICE_A";
        String SERVICE_A_LOCATION = "http://localhost:8081/";

        String SERVICE_B = "SERVICE_B";
        String SERVICE_B_LOCATION = "http://localhost:8082/";
    }

    public interface TRANSACTION_TYPE {
        String CREDIT = "CREDIT";
        String DEBIT = "DEBIT";
        String CREDIT_L = "credit";
        String DEBIT_L = "debit";
    }

    public static final int REDIRECT_STATUS_CODE = 302;
    public static final int NOT_ALLOWED_STATUS_CODE = 405;
}
