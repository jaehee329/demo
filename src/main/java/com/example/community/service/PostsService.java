package com.example.community.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.community.controller.dto.PostsResponseDto;
import com.example.community.controller.dto.PostsSaveRequestDto;
import com.example.community.domain.posts.Posts;
import com.example.community.domain.posts.PostsRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PostsService {
	private final PostsRepository postsRepository;

	public PostsResponseDto findById(Long id) {
		Posts post = postsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. 게시글 id = " + id));
		return new PostsResponseDto(post);
	}

	@Transactional
	public Long save(PostsSaveRequestDto requestDto) {
		return postsRepository.save(requestDto.toEntity()).getId();
	}
}
