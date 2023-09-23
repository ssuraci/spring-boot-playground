package it.sebastianosuraci.springboot.demo.mockmvc.base;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;


abstract class MockMvcBaseTest<D> {


	@Autowired
	private MockMvc mockMvc;

	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	@Autowired
	protected ObjectMapper objectMapper;

	protected abstract String getEndpointUrl();

	List<D> readTest(String urlParams, ResultMatcher status, String expectedJson) throws Exception {
		ResultActions resultActions = mockMvc.perform(get(getEndpointUrl()+urlParams)).andExpect(status);
		MvcResult mvcResult = resultActions.andReturn();
		String contentAsString = mvcResult.getResponse().getContentAsString();

		if (expectedJson != null) {
			Assertions.assertEquals(objectMapper.readTree(expectedJson), objectMapper.readTree(contentAsString));
		}
		return objectMapper.readValue(contentAsString, new TypeReference<List<D>>() {
		});
	}

	void writeTest(String inputJson, HttpMethod method, ResultMatcher status, String expectedJson)
			throws Exception {

		MockHttpServletRequestBuilder req = null;
		
		switch (method.name()) {
		case "POST":
			req = post(getEndpointUrl());
			break;
		case "PUT":
			req = put(getEndpointUrl());
			break;
		case "PATCH":
			req = patch(getEndpointUrl());
			break;
		default:
			throw new RuntimeException("methos not supported");
		}

		ResultActions resultActions = mockMvc
				.perform(req.contentType(APPLICATION_JSON_UTF8).content(inputJson))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON));
		MvcResult mvcResult = resultActions.andReturn();
		String contentAsString = mvcResult.getResponse().getContentAsString();

		if (expectedJson != null) {
			Assertions.assertEquals(objectMapper.readTree(expectedJson), objectMapper.readTree(contentAsString));
		}
	}

}
