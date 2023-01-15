package com.example.community.config.auth.dto;

import java.io.Serializable;

import com.example.community.domain.user.Users;

import lombok.Getter;

@Getter
public class SessionUser implements Serializable {

	private String name;
	private String email;
	private String picture;

	public SessionUser(Users user) {
		this.name = user.getName();
		this.email = user.getEmail();
		this.picture = user.getPicture();
	}
}
