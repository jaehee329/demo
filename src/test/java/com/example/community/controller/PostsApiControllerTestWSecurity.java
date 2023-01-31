package com.example.community.controller;

import static org.assertj.core.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.community.controller.dto.PostsSaveRequestDto;
import com.example.community.controller.dto.PostsUpdateRequestDto;
import com.example.community.domain.posts.Posts;
import com.example.community.domain.posts.PostsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class PostsApiControllerTestWSecurity {

	@LocalServerPort
	private int port;
	@Autowired
	private PostsRepository postsRepository;
	@Autowired
	private MockMvc mvc;
	@Autowired
	private WebApplicationContext context;

	@BeforeEach
	public void setup() {
		mvc = MockMvcBuilders
			.webAppContextSetup(context)
			.apply(springSecurity())
			.build();
	}

	@AfterEach
	public void tearDown() throws Exception {
		postsRepository.deleteAll();
	}

	@Test
	@DisplayName("포스트 저장 테스트")
	@WithMockUser(roles="USER")
	public void postsSaveTest() throws Exception {
		// given
		String title = "title";
		String content = "content";
		String author = "author";
		PostsSaveRequestDto requestDto = PostsSaveRequestDto
			.builder()
			.title(title)
			.content(content)
			.author(author)
			.build();
		String url = "http://localhost:" + port + "/api/v1/posts";

		// when
		mvc.perform(post(url)
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(requestDto)))
			.andExpect(status().isOk());

		// then
		List<Posts> posts = postsRepository.findAll();
		assertThat(posts.get(0).getTitle()).isEqualTo(title);
		assertThat(posts.get(0).getContent()).isEqualTo(content);
		assertThat(posts.get(0).getAuthor()).isEqualTo(author);
	}

	@Test
	@DisplayName("포스트 수정 테스트")
	@WithMockUser(roles="USER")
	public void postsUpdateTest() throws Exception {
		//given
		Posts savedPosts = postsRepository.save(Posts.builder()
			.title("title")
			.content("content")
			.author("author")
			.build());

		Long updateId = savedPosts.getId();
		String expectedTitle = "title2";
		String expectedContent = "content2";
		String expectedAuthor = "author2";

		PostsUpdateRequestDto requestDto = PostsUpdateRequestDto.builder()
			.title(expectedTitle)
			.content(expectedContent)
			.author(expectedAuthor)
			.build();

		String url = "http://localhost:" + port + "/api/v1/posts/" + updateId;

		//when
		mvc.perform(put(url)
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(requestDto)))
			.andExpect(status().isOk());

		//then
		List<Posts> all = postsRepository.findAll();
		assertThat(all.get(0).getTitle()).isEqualTo(expectedTitle);
		assertThat(all.get(0).getContent()).isEqualTo(expectedContent);
	}

	@Test
	@DisplayName("포스트 findById 테스트")
	@WithMockUser(roles="USER")
	public void postsFindByIdTest() throws Exception {
		String title = "title";
		String content = "content";
		String author = "author";

		Posts post = postsRepository.save(Posts.builder()
			.title(title)
			.content(content)
			.author(author)
			.build());

		String url = "http://localhost:" + port + "/api/v1/posts/" + post.getId();

		mvc.perform(get(url))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.title").value(title))
			.andExpect(jsonPath("$.content").value(content))
			.andExpect(jsonPath("$.author").value(author));
	}
}
