package com.example.demo;

import com.example.demo.controllers.ConsumerController;
import com.example.demo.entities.ExcessConsume;
import com.example.demo.entities.GetConsumed;
import com.example.demo.entities.Quota;
import com.example.demo.entities.UpdateConsumed;
import com.example.demo.repositories.ConsumeRepo;
import com.example.demo.services.ConsumerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(SpringExtension.class)
@WebMvcTest(value = ConsumerController.class)
class DemoApplicationTests {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ConsumerService consumerService;

	@MockBean
	private ConsumeRepo consumeRepo;


	@Test
	public void checkGetConsume() throws Exception {
		GetConsumed mockGotConsumed= new GetConsumed(100,400);
		Mockito.when(consumerService.consumeGet()).thenReturn(mockGotConsumed);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/consume");

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		String expected = "{\"total\": 100,\"remaining\": 400}";
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), true);
	}

	@Test
	public void checkPostConsume() throws Exception {
		GetConsumed postResponse = new GetConsumed(1000, 0);

		Mockito.when(consumerService.addValue(Mockito.any(UpdateConsumed.class))).thenReturn(postResponse);

		String postRequestBody = "{\"value\":1000}";
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/consume").content(postRequestBody)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());
		String expected = "{\"total\": 1000,\"remaining\": 0}";
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), true);

	}

	@Test
	public void checkExcessConsume() throws Exception {
		ExcessConsume postResponse = new ExcessConsume(1000);

		Mockito.when(consumerService.consumedExcess(Mockito.any(UpdateConsumed.class))).thenReturn(postResponse);

		String postRequestBody = "{\"value\":2000}";
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/consume")
				.content(postRequestBody)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.SERVICE_UNAVAILABLE.value(), response.getStatus());
		String expected = "{\"excess\": 1000}";
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), true);

	}

	@Test
	public void checkReset() throws Exception {
		GetConsumed postResponse = new GetConsumed(0, 2000);

		Mockito.when(consumerService.reset(Mockito.any(Quota.class))).thenReturn(postResponse);

		String postRequestBody = "{\"quota\":2000}";
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/reset")
				.content(postRequestBody)
				.contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());
		String expected = "{\"total\": 0,\"remaining\": 2000}";
		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), true);

	}


}
