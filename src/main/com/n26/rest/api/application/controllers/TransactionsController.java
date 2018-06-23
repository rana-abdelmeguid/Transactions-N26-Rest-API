package com.n26.rest.api.application.controllers;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.n26.rest.api.application.data.Statistics;
import com.n26.rest.api.application.data.Transaction;
import com.n26.rest.api.application.services.impl.TransactionsService;

@RestController
public class TransactionsController {

	private static final String INVALID_TRANSACTION = "204";
	private static final String SUCCESSFUL = "201";
	
	@Resource
	TransactionsService transactionsService;

	@RequestMapping(value = "/transactions", method = RequestMethod.POST)
	public String newTransaction(@RequestBody Transaction transaction) throws Exception {
		if (Boolean.TRUE.equals(transactionsService.validateAndAddTransaction(transaction))) {
			return SUCCESSFUL;
		} else {
			return INVALID_TRANSACTION;
		}
	}

	@RequestMapping(value = "/statistics", method = RequestMethod.GET)
	public Statistics statistics() throws Exception {
		return transactionsService.getStatistics();
	}
}
