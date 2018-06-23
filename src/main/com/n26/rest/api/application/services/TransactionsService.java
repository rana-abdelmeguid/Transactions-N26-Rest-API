package com.n26.rest.api.application.services;

import com.n26.rest.api.application.data.Statistics;
import com.n26.rest.api.application.data.Transaction;

public interface TransactionsService {
	Boolean validateAndAddTransaction(Transaction transaction) throws Exception;
	
	Statistics getStatistics() throws Exception;
}
