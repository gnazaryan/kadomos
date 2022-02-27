package com.kadomos.apigw.controller;

import com.kadomos.apigw.business.AccountService;
import com.kadomos.apigw.database.TransactionRepository;
import com.kadomos.apigw.database.beans.Transaction;
import com.kadomos.apigw.exception.KadomosException;
import com.kadomos.apigw.exception.KadomosUserFacingException;
import com.kadomos.apigw.response.AccountBalance;
import com.kadomos.apigw.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping(path = "account/")
public class AccountController {

    private static final Logger logger = LogManager.getLogger(AccountController.class);

    @Autowired
    private AccountService accountService;

    @GetMapping("balance")
    public AccountBalance balance() {
        AccountBalance result = new AccountBalance();
        try {
            Float balance = accountService.getAccountBalance();
            result.setValue(balance);
        } catch (Exception ex) {
            result.setSuccess(false);
            result.setError("We were not able to retrieve the account balance, please try again or contact the support");
            logger.error("Unable to retrieve the last know user account balance with error: " + ex.getMessage());
        }
        return result;
    }

    @GetMapping("transaction")
    public Response transaction(@RequestParam(name="amount", required = true) Float amount,
                                @RequestParam(name="description", required = true) String description,
                                @RequestParam(name="type", required = true) String type) {
        Response result = new Response();
        try {
            accountService.executeTransaction(amount, description, type);
        } catch (KadomosUserFacingException ex) {
            result.setSuccess(false);
            result.setError(ex.getUserFacingError());
            logger.error(ex.getUserFacingError());
            logger.error(ex.getMessage());
        } catch (Exception ex) {
            result.setSuccess(false);
            result.setError("We were not able to execute the transaction, please contact the support");
            logger.error("Unable to execute transaction for the user request with error: " + ex.getMessage());
        }
        return result;
    }
}
