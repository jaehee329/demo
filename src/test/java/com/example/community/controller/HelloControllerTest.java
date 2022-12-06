package com.example.community.controller;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

// WebMvcTest: web layer(controller)에 대해서만 테스트할 때 쓴다. 특정 컨트롤러를 지정하지 않으면 모두 불러와 톰캣이 필요한 경우 오류 발생.
@WebMvcTest(HelloController.class)
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
