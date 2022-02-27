package com.kadomos.apigw.controller;

import com.kadomos.apigw.business.AccountService;
import com.kadomos.apigw.constants.Constants;
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
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.Callable;

import static com.kadomos.apigw.constants.Constants.ACCOUNT_ADDRESS_MAPPING;
import static com.kadomos.apigw.constants.Constants.NOT_ALLOWED_STATUS_CODE;

@RestController
@RequestMapping(path = "savings/")
public class SavingsController {

    private static final Logger logger = LogManager.getLogger(SavingsController.class);

    @Autowired
    AccountService accountService;

    @GetMapping("balance")
    public Callable<AccountBalance> balance(HttpServletResponse httpServletResponse,
                                   @RequestParam(name = "accountName", required = true) String accountName) {
        return () -> {
            AccountBalance result = new AccountBalance();
            try {
                Float balance = accountService.getAccountBalance(accountName);
                result.setValue(balance);
            } catch (Exception ex) {
                result.setSuccess(false);
                result.setError("There was a technical issue when preparing the balance information, please contact the support");
            }
            return result;
        };
    }

    @GetMapping("increase")
    public Callable<Response> increase(HttpServletResponse httpServletResponse,
                                            @RequestParam(name = "accountName", required = true) String accountName,
                                             @RequestParam(name = "amount", required = true) Float amount) {
        return () -> {
            Response result = new Response();
            try {
                accountService.increase(accountName, amount);
            } catch (Exception ex) {
                result.setSuccess(false);
                result.setError("There was a technical issue when executing increase transaction, please contact the support");
            }
            return result;
        };
    }

    @GetMapping("decrease")
    public Callable<Response> decrease(HttpServletResponse httpServletResponse,
                                       @RequestParam(name = "accountName", required = true) String accountName,
                                       @RequestParam(name = "amount", required = true) Float amount) {
        return () -> {
            Response result = new Response();
            try {
                accountService.decrease(accountName, amount);
            } catch (Exception ex) {
                result.setSuccess(false);
                result.setError("There was a technical issue when executing increase transaction, please contact the support");
            }
            return result;
        };
    }
}
