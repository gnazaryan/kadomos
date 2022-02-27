package com.kadomos.apigw.business;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kadomos.apigw.constants.Constants;
import com.kadomos.apigw.exception.KadomosException;
import com.kadomos.apigw.exception.KadomosUserFacingException;
import com.kadomos.apigw.response.AccountBalance;
import com.kadomos.apigw.response.Response;
import com.kadomos.apigw.util.HttpUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

import static com.kadomos.apigw.constants.Constants.ACCOUNT_ADDRESS_MAPPING;

@Service
public class AccountServiceImpl implements AccountService{

    private static final Logger logger = LogManager.getLogger(AccountServiceImpl.class);

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Float getAccountBalance(String accountName) throws KadomosException {
        String accountAddress = ACCOUNT_ADDRESS_MAPPING.get(accountName) + Constants.SERVICES.ACCOUNT_BALANCE;
        try {
            String response = HttpUtil.httpGetRequest(accountAddress, null);
            AccountBalance accountBalance = objectMapper.readValue(response, AccountBalance.class);
            return accountBalance.getValue();
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new KadomosException(e.getMessage());
        }
    }

    @Override
    public void increase(String accountName, Float amount) throws KadomosException {
        String accountAddress = ACCOUNT_ADDRESS_MAPPING.get(accountName) + Constants.SERVICES.ACCOUNT_TRANSACTION;
        try {
            Map<String, String> parameters = new HashMap<>();
            parameters.put(Constants.REQUEST_PROPERTIES.ACCOUNT_NAME, accountName);
            parameters.put(Constants.REQUEST_PROPERTIES.DESCRIPTION, "Increase of balance");
            parameters.put(Constants.REQUEST_PROPERTIES.AMOUNT, amount.toString());
            parameters.put(Constants.REQUEST_PROPERTIES.TYPE, Constants.TRANSACTION_TYPE.CREDIT);
            String responseJson = HttpUtil.httpGetRequest(accountAddress, parameters);
            Response response = objectMapper.readValue(responseJson, AccountBalance.class);
            if (!response.isSuccess()) {
                throw new KadomosException("There was an error executing increase transaction with error: " + response.getError());
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new KadomosUserFacingException(e.getMessage(),
                    "There was a technical issue when executing the transaction, please contact the support");
        }
    }

    @Override
    public void decrease(String accountName, Float amount) throws KadomosException {
        String accountAddress = ACCOUNT_ADDRESS_MAPPING.get(accountName) + Constants.SERVICES.ACCOUNT_TRANSACTION;
        try {
            Map<String, String> parameters = new HashMap<>();
            parameters.put(Constants.REQUEST_PROPERTIES.ACCOUNT_NAME, accountName);
            parameters.put(Constants.REQUEST_PROPERTIES.DESCRIPTION, "Increase of balance");
            parameters.put(Constants.REQUEST_PROPERTIES.AMOUNT, amount.toString());
            parameters.put(Constants.REQUEST_PROPERTIES.TYPE, Constants.TRANSACTION_TYPE.DEBIT);
            String responseJson = HttpUtil.httpGetRequest(accountAddress, parameters);
            Response response = objectMapper.readValue(responseJson, AccountBalance.class);
            if (!response.isSuccess()) {
                throw new KadomosException("There was an error executing increase transaction with error: " + response.getError());
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new KadomosUserFacingException(e.getMessage(),
                    "There was a technical issue when executing the transaction, please contact the support");
        }
    }
}
