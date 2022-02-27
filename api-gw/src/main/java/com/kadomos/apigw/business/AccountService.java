package com.kadomos.apigw.business;

import com.kadomos.apigw.exception.KadomosException;

/**
 * The service is responsible for delegating the gateway requests to
 * corresponding savings accounts
 */
public interface AccountService {

    /**
     * The Api makes an http request to the specified account and fetches balance information
     * @param accountName
     * @return the balance value
     * @throws KadomosException
     */
    Float getAccountBalance(String accountName) throws KadomosException;

    /**
     * Increase the balance with the specified amount
     * @param amount the amount to increase the balance
     * @param accountName the account name to decrease the amount
     * @throws KadomosException
     */
    void increase(String accountName, Float amount) throws KadomosException;

    /**
     * Decrease the balance with the specified amount
     * @param amount the amount to decrease the balance
     * @param accountName the account name to decrease the amount
     * @throws KadomosException
     */
    void decrease(String accountName, Float amount) throws KadomosException;
}
