package com.example.community.controller.dto;

import com.example.community.domain.posts.Posts;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

// Entity인 Posts객체를 바로 생성하는 대신 빌더패턴을 사용하여 DTO로 Posts 객체를 생성해 넘겨준다.
@Getter
@NoArgsConstructor
public class PostsSaveRequestDto {

	private String title;
	private String content;
	private String author;

	@Builder
	public PostsSaveRequestDto(String title, String content, String author) {
		this.title = title;
		this.content = content;
		this.author = author;
	}

	public Posts toEntity() {
		return Posts.builder()
					.title(title)
					.content(content)
					.author(author)
					.build();
	}
}
