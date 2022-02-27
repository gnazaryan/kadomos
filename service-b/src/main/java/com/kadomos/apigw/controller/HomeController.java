package com.kadomos.apigw.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    private static final Logger logger = LogManager.getLogger(HomeController.class);

    @GetMapping("/")
	public String index() {
        logger.info("Greetings from Account B!");
		return "Greetings from Account B!";
	}
}
