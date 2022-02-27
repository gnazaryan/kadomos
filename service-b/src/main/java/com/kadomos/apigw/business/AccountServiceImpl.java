package com.kadomos.apigw.business;

import com.kadomos.apigw.constants.Constants;
import com.kadomos.apigw.database.TransactionRepository;
import com.kadomos.apigw.database.beans.Transaction;
import com.kadomos.apigw.exception.KadomosException;
import com.kadomos.apigw.exception.KadomosUserFacingException;
import com.kadomos.apigw.response.AccountBalance;
import com.kadomos.apigw.util.Convenience;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    TransactionRepository transactionRepository;


    @Override
    public Float getAccountBalance() throws KadomosException {
        synchronized (AccountServiceImpl.class) {
            return getAccountBalanceInternal();
        }
    }

    @Override
    public void executeTransaction(Float amount, String description, String type) throws KadomosException {
        synchronized (AccountServiceImpl.class) {
            executeTransactionInternal(amount, description, type);
        }
    }

    private Float getAccountBalanceInternal() throws KadomosException {
        List<Transaction> transactions = transactionRepository.findLastCreated();
        if (Convenience.isNotNullOrEmptyList(transactions)) {
            return transactions.get(0).getBalance();
        }
        //If no transaction was found, assume balance is 0
        return 0f;
    }

    private void executeTransactionInternal(Float amount, String description, String type) throws KadomosException {
        //Take the absolute value of the amount to nigatet he negative
        amount = Math.abs(amount);
        Float balance = getAccountBalance();
        Float newBalance = 0f;
        String transactionType = null;
        switch(type) {
            case Constants.TRANSACTION_TYPE.DEBIT_L:
            case Constants.TRANSACTION_TYPE.DEBIT:
                if (balance < amount) {
                    throw new KadomosUserFacingException("The transaction was not able to execute due to non technical reasons",
                            "The balance on the account is not enough to execute the transaction");
                }
                newBalance = balance - amount;
                transactionType = Constants.TRANSACTION_TYPE.DEBIT;
                break;
            case Constants.TRANSACTION_TYPE.CREDIT_L:
            case Constants.TRANSACTION_TYPE.CREDIT:
                newBalance = balance + amount;
                transactionType = Constants.TRANSACTION_TYPE.CREDIT;
                break;
            default:
                throw new KadomosException("Invalid type of transaction was supplied, interupting the execution");
        }
        Long createdDate = System.currentTimeMillis();
        Integer result = transactionRepository.insert(description, amount, newBalance, createdDate, createdDate, transactionType);
        if (result == null || result != 1) {
            throw new KadomosException("The database transaction insert operation didn't go successfully for amount: " + amount + ", description: " + description);
        }
    }
}
