package com.example.community.web;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

@Getter
public class PostForm {

	@NotEmpty(message="게시글 제목은 필수입니다")
	private String title;

	private String content;
	private String author;
}
