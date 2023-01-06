package com.example.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.community.controller.dto.PostsResponseDto;
import com.example.community.service.PostsService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class IndexController {

	private final PostsService postsService;

	@GetMapping("/")
	public String home(Model model) {
		return "index";
	}

	@GetMapping("/posts")
	public String posts(Model model) {
		model.addAttribute("posts", postsService.findAllDesc());
		return "posts";
	}

	@GetMapping("/posts/{id}")
	public String specificPost(@PathVariable Long id, Model model) {
		PostsResponseDto dto = postsService.findById(id);
		model.addAttribute("post", dto);
		return "post-specific";
	}

	@GetMapping("/posts/{id}/edit")
	public String postUpdate(@PathVariable Long id, Model model) {
		PostsResponseDto dto = postsService.findById(id);
		model.addAttribute("post", dto);
		return "posts-update";
	}

	@GetMapping("/posts/save")
	public String postsSave() {
		return "posts-save";
	}
}
