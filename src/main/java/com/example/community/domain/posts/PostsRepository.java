package com.example.community.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostsRepository extends JpaRepository<Posts, Long> {
}

/*
Entity 클래스와 Entity에 대한 Repository는 함께 위치하도록 구성.
추후 도메인별로 프로젝트 분리 시에도 Entity와 Repository의 묶음 단위로 분리한다.
 */
