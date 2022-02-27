package com.kadomos.apigw.controller;

import com.kadomos.apigw.constants.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
@RequestMapping(path = "gateway/")
public class GatewayController {

    private static final Logger logger = LogManager.getLogger(GatewayController.class);

    @GetMapping("balance")
    public Callable<RedirectView> balance(HttpServletResponse httpServletResponse,
                                   @RequestParam(name = "accountName", required = true) String accountName) {
        return () -> {
            RedirectView result = new RedirectView();
            logger.info("Redirect balance request for account: " + accountName);
            if (ACCOUNT_ADDRESS_MAPPING.containsKey(accountName)) {
                result.setUrl(ACCOUNT_ADDRESS_MAPPING.get(accountName) + Constants.SERVICES.ACCOUNT_BALANCE);
            } else {
                httpServletResponse.setStatus(NOT_ALLOWED_STATUS_CODE);
            }
            return result;
        };
    }

    @GetMapping("transaction")
    public Callable<RedirectView> redirect(HttpServletResponse httpServletResponse,
                                           @RequestParam(name = "accountName", required = true) String accountName,
                                           @RequestParam(name = "description", required = true) String description,
                                           @RequestParam(name = "amount", required = true) String amount,
                                           @RequestParam(name = "type", required = true) String type) {
        return () -> {
            RedirectView result = new RedirectView();
            logger.info("Redirect balance request for account: " + accountName);
            if (ACCOUNT_ADDRESS_MAPPING.containsKey(accountName)) {
                result.setUrl(ACCOUNT_ADDRESS_MAPPING.get(accountName) + Constants.SERVICES.ACCOUNT_TRANSACTION);
                result.addStaticAttribute("description", description);
                result.addStaticAttribute("amount", amount);
                result.addStaticAttribute("type", type);
            } else {
                httpServletResponse.setStatus(NOT_ALLOWED_STATUS_CODE);
            }
            return result;
        };
    }
}
