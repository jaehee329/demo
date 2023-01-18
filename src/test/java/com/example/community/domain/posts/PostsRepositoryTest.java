package com.example.community.domain.posts;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PostsRepositoryTest {
	//
	// @Autowired
	// PostsRepository postsRepository;
	//
	// @AfterEach
	// public void cleanup() {
	// 	postsRepository.deleteAll();
	// }
	//
	// @Test
	// @DisplayName("게시글 저장 및 전체 불러오기 테스트")
	// public void postsSaveFindAllTest() {
	// 	// given
	// 	String title = "title";
	// 	String content = "content";
	// 	String author = "jjhs9803@gmail.com";
	//
	// 	postsRepository.save(Posts.builder()
	// 		.title(title)
	// 		.content(content)
	// 		.author(author)
	// 		.build());
	//
	// 	// when
	// 	List<Posts> postsList = postsRepository.findAll();
	//
	// 	// then
	// 	Posts posts = postsList.get(0);
	// 	assertThat(posts.getTitle()).isEqualTo(title);
	// 	assertThat(posts.getContent()).isEqualTo(content);
	// 	assertThat(posts.getAuthor()).isEqualTo(author);
	// }
	//
	// @Test
	// @DisplayName("BaseTimeEntity가 추가적으로 등록되어야 한다")
	// public void BaseTimeEntityTest() {
	//
	// 	// given
	// 	LocalDateTime now = LocalDateTime.now();
	// 	postsRepository.save(Posts.builder()
	// 			.title("title")
	// 			.content("content")
	// 			.author("author")
	// 			.build());
	// 	// when
	// 	List<Posts> postsList = postsRepository.findAll();
	//
	// 	// then
	// 	Posts posts = postsList.get(0);
	// 	assertThat(posts.getCreatedDate()).isAfter(now);
	// 	assertThat(posts.getModifiedDate()).isAfter(now);
	// }
}
