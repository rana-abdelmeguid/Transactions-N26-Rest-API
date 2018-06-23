package com.n26.rest.api.application.services.impl;

import java.time.Clock;
import java.time.Instant;
import java.util.concurrent.Callable;

import org.springframework.stereotype.Component;

import com.n26.rest.api.application.data.Statistics;
import com.n26.rest.api.application.data.Transaction;

@Component
public class TransactionsService {
	private static final int MS_TO_SEC = 1000;
	private static final int VALID_TRANSACTION_IN_SEC = 60;
	private Statistics statistics = new Statistics(0, 0, Integer.MAX_VALUE, 0, 0);
	
	public Boolean validateAndAddTransaction(Transaction transaction) throws Exception {
		Callable<Boolean> thread = new Callable<Boolean>() {
			public Boolean call() {
				if ((getCurrentTimestamp() - transaction.getTimestamp()) / MS_TO_SEC <= VALID_TRANSACTION_IN_SEC) {
					calculateStatistics(transaction.getAmount());
					return Boolean.TRUE;
				} else {
					return Boolean.FALSE;
				}
			}
		};
		return thread.call();
	}

	
	public Statistics getStatistics() throws Exception {
		Callable<Statistics> thread = new Callable<Statistics>() {
			public Statistics call() {
				return statistics;
			}
		};
		return thread.call();
	}

	protected long getCurrentTimestamp() {
		return Instant.now(Clock.systemUTC()).toEpochMilli();
	}

	protected synchronized void calculateStatistics(double amount) {
		statistics.setSum(statistics.getSum() + amount);
		statistics.setMax(amount > statistics.getMax() ? amount : statistics.getMax());
		statistics.setMin(amount < statistics.getMin() ? amount : statistics.getMin());
		statistics.setCount(statistics.getCount() + 1L);
		statistics.setAvg(statistics.getSum() / statistics.getCount());
	}
}
