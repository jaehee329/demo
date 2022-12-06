package com.example.community.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.community.controller.dto.PostsSaveRequestDto;
import com.example.community.service.PostsService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class PostsApiController {

	private final PostsService postsService;

	@PostMapping("/api/v1/posts")
	public Long save(@RequestBody PostsSaveRequestDto postsSaveRequestDto) {
		return postsService.save(postsSaveRequestDto);
	}
}
