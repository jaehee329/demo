package com.example.community.config.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import com.example.community.domain.user.Role;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

	private final CustomOAuth2UserService customOAuth2UserService;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http
			.csrf(csrf -> csrf.disable())
			.authorizeHttpRequests(auth -> auth
				.requestMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**").permitAll()
				.requestMatchers("/api/v1**").hasRole(Role.USER.name())
				.anyRequest().authenticated())
			.logout().logoutSuccessUrl("/");

		http
			.oauth2Login(oauth2 -> oauth2
				.userInfoEndpoint().userService(customOAuth2UserService));
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
