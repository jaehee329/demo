package com.example.community.controller;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.community.controller.dto.PostsSaveRequestDto;
import com.example.community.domain.posts.Posts;
import com.example.community.domain.posts.PostsRepository;

/*
webEnvironment를 설정하지 않으면 실제 서블릿 컨테이너를 mocking한 것을 사용
아래처럼 랜덤 포트를 사용하도록 하면 테스트 중 톰캣이 랜덤한 포트로 설정, 기본 설정인 mocking시엔 없었던 TestRestTemplate의 빈을 사용 가능.
기본 설정인 MockMvc에선 서블릿 컨테이너(톰캣) 구동 없이 mock 서블릿으로 요청을 처리. (+WebMvcTest도 톰캣이 뜨지도 않음)
TestRestTemplate은 톰캣을 직접 띄워 클라이언트 입장에서 RestTemplate을 사용해 테스트 가능.
Springboot 테스트와 테스트 유틸: https://cheolhojung.github.io/posts/java/springboot-test.html
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PostsApiControllerTest {

	// 현재 서버가 사용중인 포트 정보 로드, 기본 8080이나 Random_port로 설정해 바꼈을 듯.
	@LocalServerPort
	private int port;

	@Autowired
	private PostsRepository postsRepository;

	/*
	https://tecoble.techcourse.co.kr/post/2021-07-25-resttemplate-webclient/
	책에서는 Spring 3.0부터 지원된 resttemplate을 사용하나 이것이 곧 deprecated되어 5.0부터 지원하는 webClient로도 구현한다.
 	*/
	@Autowired
	private TestRestTemplate restTemplate;

	@AfterEach
	public void tearDown() {
		postsRepository.deleteAll();
	}

	@Test
	@DisplayName("포스트 저장 테스트, restTemplate 사용")
	public void postsSaveTest1() {
		// given
		String title = "title";
		String content = "content";
		String author = "jjhs9803@gmail.com";
		PostsSaveRequestDto requestDto = PostsSaveRequestDto
										.builder()
										.title(title)
										.content(content)
										.author(author)
										.build();
		String url = "http://localhost:" + port +"/api/v1/posts";

		// when
		ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);

		// then
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(responseEntity.getBody()).isGreaterThan(0L);

		List<Posts> all = postsRepository.findAll();
		assertThat(all.get(0).getTitle()).isEqualTo(title);
		assertThat(all.get(0).getContent()).isEqualTo(content);
		assertThat(all.get(0).getAuthor()).isEqualTo(author);
	}

	/*
	webClient 장점: 1. Async(요청 후 처리 여부 확인 X, 콜백함수로 응답 도착하면 그 때 처리),
				2. non-blocking(요청 이후 응답을 기다리지 않고 다른 일 함) 방식
	webClient 참고 링크
	전반적인 사용법 및 Async non blocking 장점: https://gngsn.tistory.com/154
	응답 변환: https://stackoverflow.com/questions/67975574/how-to-convert-webclient-response-to-responseentity
	 */
	@Test
	@DisplayName("포스트 저장 테스트, webClient 사용")
	public void postsSaveTest2() {
		// given
		String title = "title";
		String content = "content";
		String author = "jjhs9803@gmail.com";
		PostsSaveRequestDto requestDto = PostsSaveRequestDto
			.builder()
			.title(title)
			.content(content)
			.author(author)
			.build();
		String baseUrl = "http://localhost:" + port;
		String followingUrl = "/api/v1/posts";

		// when
		WebClient webClient = WebClient.builder()
			.baseUrl(baseUrl)
			.build();

		ResponseEntity<Long> responseEntity = webClient.post()
			.uri(followingUrl)
			.bodyValue(requestDto)
			.retrieve()
			.toEntity(Long.class)
			.block();

		// then
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(responseEntity.getBody()).isGreaterThan(0L);

		List<Posts> all = postsRepository.findAll();
		assertThat(all.get(0).getTitle()).isEqualTo(title);
		assertThat(all.get(0).getContent()).isEqualTo(content);
		assertThat(all.get(0).getAuthor()).isEqualTo(author);
	}

	@Test
	@DisplayName("포스트 findById 테스트")
	public void postsFindByIdTest() {
		// given
		String title = "title";
		String content = "content";
		String author = "jjhs9803@gmail.com";
		PostsSaveRequestDto requestDto = PostsSaveRequestDto
			.builder()
			.title(title)
			.content(content)
			.author(author)
			.build();
		String baseUrl = "http://localhost:" + port;
		String followingUrl = "/api/v1/posts";

		// when
		WebClient webClient = WebClient.builder()
			.baseUrl(baseUrl)
			.build();

		ResponseEntity<Long> responseEntity = webClient.post()
			.uri(followingUrl)
			.bodyValue(requestDto)
			.retrieve()
			.toEntity(Long.class)
			.block();

		// then
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(responseEntity.getBody()).isGreaterThan(0L);

		List<Posts> all = postsRepository.findAll();
		assertThat(all.get(0).getTitle()).isEqualTo(title);
		assertThat(all.get(0).getContent()).isEqualTo(content);
		assertThat(all.get(0).getAuthor()).isEqualTo(author);
	}


}
