package com.kadomos.apigw.business;

import com.kadomos.apigw.exception.KadomosException;
import com.kadomos.apigw.response.AccountBalance;

/**
 * The service providing business level functionalities for the account.
 * Account services are run in a synchronised manner which makes sure it supports concurrency
 */
public interface AccountService {

    /**
     * Retrieve the last know account balance
     * @return the float value of the balance
     */
    Float getAccountBalance() throws KadomosException;

    /**
     * make a transaction on the account for the provided amount
     * @param amount the amount of the transaction
     * @param description the description of the transaction
     * @param type the type of transaction - credit or debit
     * @return true if the transaction was successful
     * @throws KadomosException
     */
    void executeTransaction(Float amount, String description, String type) throws KadomosException;

}
