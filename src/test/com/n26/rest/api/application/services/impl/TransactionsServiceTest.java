package com.n26.rest.api.application.services.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import com.n26.rest.api.application.data.Transaction;

@RunWith(MockitoJUnitRunner.class)
public class TransactionsServiceTest {
	private Transaction transaction;
	
	private TransactionsService transactionsService;

	@Before
	public void setup() {
		transaction = new Transaction();
		transaction.setAmount(12.5);
		transactionsService = spy(TransactionsService.class);
		when(transactionsService.getCurrentTimestamp()).thenReturn(120000L);
	}

	@Test
	public void testInvalidTransactionAddition() throws Exception {
		transaction.setTimestamp(60L);
		Boolean isValid = transactionsService.validateAndAddTransaction(transaction);
		assertEquals(Boolean.FALSE, isValid);
	}

	@Test
	public void testValidTransactionAddition() throws Exception {
		transaction.setTimestamp(60000L);
		Boolean isValid = transactionsService.validateAndAddTransaction(transaction);
		assertEquals(Boolean.TRUE, isValid);
		verify(transactionsService).calculateStatistics(transaction.getAmount());
	}
}
