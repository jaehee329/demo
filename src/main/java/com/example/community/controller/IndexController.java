package com.example.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.community.service.PostsService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class IndexController {

	private final PostsService postsService;

	@GetMapping("/")
	public String index(Model model) {
		return "index";
	}

	@GetMapping("/posts")
	public String posts(Model model) {
		return "posts";
	}

	@GetMapping("/posts/save")
	public String postsSave() {
		return "posts-save";
	}
}
