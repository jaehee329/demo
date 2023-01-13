package com.example.community.config.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;

import com.example.community.domain.user.Role;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

	private final CustomOAuth2UserService customOAuth2UserService;

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().requestMatchers("/h2-console/**");
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http
			// .csrf().ignoringRequestMatchers("/h2-console/**")
			// .and()
			.csrf().disable()
			.headers().addHeaderWriter(new XFrameOptionsHeaderWriter(XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN))
			.and()
				.authorizeHttpRequests(auth -> auth
					.requestMatchers( "/", "/favicon.ico", "/posts/**", "/css/**", "/images/**", "/js/**", "/h2-console/**", "/error/**", "/register").permitAll()
					// .requestMatchers( "/h2-console/**", "/error/**").permitAll()
					// .requestMatchers("/", "/h2-console/**", "/posts/**", "/error/**", "/favicon.ico").permitAll()
					.anyRequest().authenticated())
				.logout().logoutSuccessUrl("/")
			.and()
				.oauth2Login().userInfoEndpoint().userService(customOAuth2UserService);

		return http.build();
	}
}

/*
WebSecurityConfigurerAdapter가 deprecated되어 대신 SecurityFilterChain을 Bean에 등록하여 사용한다.
authorizeRequests(), antMatchers()등의 API가 사용 불가능해지며 대신 위와 같이 사용한다.

Reference:
https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter
https://docs.spring.io/spring-security/reference/servlet/oauth2/login/advanced.html
https://stackoverflow.com/questions/74683225/updating-to-spring-security-6-0-replacing-removed-and-deprecated-functionality
 */

// TODO: h2-console을 접근 허용시켜놓았으나 계속 oauth 인증을 처리해야 하는 문제. 해결 방법을 아직 모름.
