package com.kadomos.apigw.constants;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

public class Constants {

    public static final String REQUEST_LOG_FORMAT = "URL: %s, PARAMETERS: %s";

    public interface KADOMOS_ACCOUNTS {
        String ACCOUNT_A = "ACCOUNT_A";
        String SERVICE_A_LOCATION = "http://localhost:8081/";

        String ACCOUNT_B = "ACCOUNT_B";
        String SERVICE_B_LOCATION = "http://localhost:8082/";
    }

    public interface TRANSACTION_TYPE {
        String CREDIT = "CREDIT";
        String DEBIT = "DEBIT";
        String CREDIT_L = "credit";
        String DEBIT_L = "debit";
    }

    public interface REQUEST_PROPERTIES {
        String ACCOUNT_NAME = "accountName";
        String DESCRIPTION = "description";
        String AMOUNT = "amount";
        String TYPE = "type";
    }

    //Mapping for the account to the address and port number th e account is located
    public static Map<String, String> ACCOUNT_ADDRESS_MAPPING = new ImmutableMap.Builder<String, String>()
            .put(KADOMOS_ACCOUNTS.ACCOUNT_A, KADOMOS_ACCOUNTS.SERVICE_A_LOCATION)
            .put(KADOMOS_ACCOUNTS.ACCOUNT_B, KADOMOS_ACCOUNTS.SERVICE_B_LOCATION)
            .build();

    public interface SERVICES {
        String ACCOUNT_BALANCE = "account/balance";
        String ACCOUNT_TRANSACTION = "account/transaction";
    }
    public static final int REDIRECT_STATUS_CODE = 302;
    public static final int NOT_ALLOWED_STATUS_CODE = 405;
}
