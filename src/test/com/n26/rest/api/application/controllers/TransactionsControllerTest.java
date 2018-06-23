package com.n26.rest.api.application.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.n26.rest.api.application.Application;
import com.n26.rest.api.application.data.Statistics;
import com.n26.rest.api.application.services.impl.TransactionsService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class)
public class TransactionsControllerTest {

	private MockMvc mockMvc;

	@InjectMocks
	private TransactionsController transactionsController;

	@Mock
	private TransactionsService transactionsService;

	String exampleTransaction = "{\"amount\" : 50, \"timestamp\" : 6000}";

	@Before
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = standaloneSetup(transactionsController).build();
	}

	@Test
	public void testValidTransactions() throws Exception {
		when(transactionsService.validateAndAddTransaction(Mockito.any())).thenReturn(Boolean.TRUE);
		MvcResult result = this.mockMvc.perform(post("/transactions").accept(MediaType.APPLICATION_JSON)
				.content(exampleTransaction).contentType(MediaType.APPLICATION_JSON)).andReturn();
		assertEquals("201", result.getResponse().getContentAsString());
	}

	@Test
	public void testInvalidTransactions() throws Exception {
		when(transactionsService.validateAndAddTransaction(Mockito.any())).thenReturn(Boolean.FALSE);
		MvcResult result = this.mockMvc.perform(post("/transactions").accept(MediaType.APPLICATION_JSON)
				.content(exampleTransaction).contentType(MediaType.APPLICATION_JSON)).andReturn();
		assertEquals("204", result.getResponse().getContentAsString());
	}

	@Test
	public void testGetStatistics() throws Exception {
		Statistics statistics = new Statistics(30, 15, 10, 20, 2);
		when(transactionsService.getStatistics()).thenReturn(statistics);
		this.mockMvc.perform(get("/statistics")).andExpect(status().isOk()).andExpect(jsonPath("sum").value(30.0))
				.andExpect(jsonPath("avg").value(15.0)).andExpect(jsonPath("min").value(10.0))
				.andExpect(jsonPath("max").value(20.0)).andExpect(jsonPath("count").value(2.0));
	}
}
