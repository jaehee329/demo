package com.example.community.domain.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Long> {

	// 이미 가입된 사용자인지 확인할 때 사용
	Optional<Users> findByEmail(String email);
}
