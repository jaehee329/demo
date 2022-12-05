package com.example.community.controller;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

// @SpringBootTest
@WebMvcTest
class HelloControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	@DisplayName("hello가 리턴되어야 한다")
	public void helloTest() throws Exception {
		mockMvc.perform(get("/hello")).andExpectAll(
			status().isOk(),
			content().string("hello"));
	}

	@Test
	@DisplayName("HelloResponseDto가 리턴되어야 한다")
	public void helloDtoTest() throws Exception {
		String name = "kim";
		int amount = 1000;

		mockMvc.perform(get("/hello/dto").param("name", name).param("amount", String.valueOf(amount)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.name", is(name)))
			.andExpect(jsonPath("$.amount", is(amount)));
	}
}
