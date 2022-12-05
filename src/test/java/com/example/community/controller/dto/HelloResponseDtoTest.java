package com.example.community.controller.dto;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HelloResponseDtoTest {

	@Test
	@DisplayName("롬복 기능 테스트")
	void lombokTest() {
		// given
		String name = "test";
		int amount = 1000;

		// when
		HelloResponseDto helloResponseDto = new HelloResponseDto(name, amount);

		// then
		assertThat(helloResponseDto.getName()).isEqualTo(name);
		assertThat(helloResponseDto.getAmount()).isEqualTo(amount);

	}

}
