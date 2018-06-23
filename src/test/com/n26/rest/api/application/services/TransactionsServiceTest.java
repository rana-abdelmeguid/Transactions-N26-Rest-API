package com.n26.rest.api.application.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import com.n26.rest.api.application.data.Statistics;
import com.n26.rest.api.application.data.Transaction;
import com.n26.rest.api.application.services.impl.TransactionsServiceImpl;

public class TransactionsServiceTest {
	TransactionsServiceImpl transactionsService;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(TransactionsServiceTest.class);
		transactionsService = new TransactionsServiceImpl();
	}

	@Test
	public void testInvalidTransactionAddition() throws Exception {
		Transaction transaction = new Transaction();
		transaction.setTimestamp(60L);
		transaction.setAmount(12.5);
		TransactionsServiceImpl transactionServiceMock = mock(TransactionsServiceImpl.class);
		when(transactionServiceMock.getCurrentTimestamp()).thenReturn(1200000L);
		Boolean isValid = transactionServiceMock.validateAndAddTransaction(transaction);
		assertEquals(Boolean.FALSE, isValid);
	}

	@Test
	public void testValidTransactionAddition() throws Exception {
		Transaction transaction = new Transaction();
		transaction.setTimestamp(60000L);
		transaction.setAmount(12.5);
		TransactionsServiceImpl transactionServiceMock = mock(TransactionsServiceImpl.class);
		when(transactionServiceMock.getCurrentTimestamp()).thenReturn(1200000L);
		Boolean isValid = transactionServiceMock.validateAndAddTransaction(transaction);
		assertEquals(Boolean.TRUE, isValid);
		verify(transactionServiceMock).calculateStatistics(transaction.getAmount());
	}
}
