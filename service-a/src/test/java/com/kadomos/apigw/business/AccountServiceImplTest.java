package com.kadomos.apigw.business;

import com.kadomos.apigw.controller.AccountController;
import com.kadomos.apigw.database.TransactionRepository;
import com.kadomos.apigw.database.beans.Transaction;
import com.kadomos.apigw.exception.KadomosRuntimeException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AccountServiceImplTest {

    private static final Logger logger = LogManager.getLogger(AccountServiceImplTest.class);

    @Autowired
    TransactionRepository transactionRepository;

	@Autowired
	private AccountService accountService;

	@Test
	public void getAccountBalanceTest() throws Exception {
        Float balance = accountService.getAccountBalance();
        accountService.executeTransaction(26.9f, "Description", "credit");
        Float newBalance = accountService.getAccountBalance();
        assertEquals(balance + 26.9f, newBalance);
	}

    /**
     * The test runs concurent test on the transaction api
     * in case the synchronisation blocks are commented in the accountService
     * this test will likely fail, confirming the synchronisation importance of the account service
     * @throws Exception
     */
    @Test
    public void executeTransactionMultithreaded() throws Exception {
        BalanceRunnable balanceRunnable = new BalanceRunnable();
        Thread one = new Thread(balanceRunnable);
        one.setName("One");
        Thread two = new Thread(balanceRunnable);
        two.setName("Two");
        Thread three = new Thread(balanceRunnable);
        three.setName("Three");
        Float balance = accountService.getAccountBalance();
        one.start();
        two.start();
        three.start();
        Thread.sleep(5000);
        Float newBalance = accountService.getAccountBalance();
        assertEquals(balance + 15f, newBalance);
    }

    private class BalanceRunnable implements Runnable {
        @Override
        public void run() {
            try {
                for (int i = 0; i < 25; i++) {
                    accountService.executeTransaction(5f, "Description", (i % 2 == 0) ? "credit" : "debit");
                }
            } catch (Exception ex) {
                String error = "AccountServiceImplTest - unable to execute transaction with error: " + ex.getMessage();
                logger.error(error);
                throw new KadomosRuntimeException(error);
            }
        }
    }
}
