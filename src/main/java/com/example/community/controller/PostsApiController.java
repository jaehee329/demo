package com.example.community.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.community.controller.dto.PostsResponseDto;
import com.example.community.controller.dto.PostsSaveRequestDto;
import com.example.community.controller.dto.PostsUpdateRequestDto;
import com.example.community.service.PostsService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class PostsApiController {

	private final PostsService postsService;

	@GetMapping("/api/v1/posts/{id}")
	public PostsResponseDto findById(@PathVariable Long id) {
		return postsService.findById(id);
	}

	@PostMapping("/api/v1/posts")
	public Long save(@RequestBody PostsSaveRequestDto postsSaveRequestDto) {
		return postsService.save(postsSaveRequestDto);
	}

	@PutMapping("/api/v1/posts/{id}")
	public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto) {
		return postsService.update(id, requestDto);
	}
}
