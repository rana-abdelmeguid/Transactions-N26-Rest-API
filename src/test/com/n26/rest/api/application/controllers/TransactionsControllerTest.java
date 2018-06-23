package com.n26.rest.api.application.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.n26.rest.api.application.data.Transaction;
import com.n26.rest.api.application.services.TransactionsService;

@RunWith(SpringRunner.class)
@WebMvcTest(TransactionsController.class)
public class TransactionsControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private TransactionsService transactionsService;

	@Test
	public void testValidTransactions() throws Exception {
		Transaction transaction = new Transaction();
		when(transactionsService.validateAndAddTransaction(transaction)).thenReturn(Boolean.TRUE);
		MvcResult result = this.mockMvc.perform(post("/transactions").contentType(MediaType.APPLICATION_JSON)
				.content(convertObjectToString(transaction))).andExpect(status().isOk()).andReturn();
		assertEquals("201", result.getResponse().getContentAsString());
	}

	@Test
	public void testInvalidTransactions() throws Exception {
		Transaction transaction = new Transaction();
		when(transactionsService.validateAndAddTransaction(transaction)).thenReturn(Boolean.FALSE);
		MvcResult result = this.mockMvc.perform(post("/transactions").contentType(MediaType.APPLICATION_JSON)
				.content(convertObjectToString(transaction))).andExpect(status().isOk()).andReturn();
		assertEquals("204", result.getResponse().getContentAsString());
	}

	@Test
	public void testGetStatistics() throws Exception {
		this.mockMvc.perform(get("/statistics")).andExpect(status().isOk()).andExpect(jsonPath("sum").value(0.0))
				.andExpect(jsonPath("avg").value(0.0)).andExpect(jsonPath("min").value(Integer.MAX_VALUE))
				.andExpect(jsonPath("max").value(0.0)).andExpect(jsonPath("count").value(0.0));
	}

	private static String convertObjectToString(Object object) {
		try {
			return new ObjectMapper().writeValueAsString(object);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}
}
