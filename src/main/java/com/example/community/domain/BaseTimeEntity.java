/*
엔티티에 저장되는 정보들은 생성, 수정 시간을 함께 기록하는 것이 유지보수에 있어 필수적.
Post와 같은 DB의 데이터를 다룰 때마다 시간 관련 코드를 추가해줘야 하는 것은 매우 불편.
JPA Auditing을 사용해 간단히 해결한다.

BaseTimeEntity 클래스를 만들고 모든 엔티티가 이 클래스를 상속해 동일한 컬럼을 가지도록 한다.
해당 클래스는 최상위 Application 클래스에 활성화 어노테이션을 추가하고,
Posts 클래스가 상속받도록 설정하면 사용된다.
 */

package com.example.community.domain;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTimeEntity {

	@CreatedDate
	private LocalDateTime createdDate;

	@LastModifiedDate
	private LocalDateTime modifiedDate;
}
/*
@MappedSuperClass: 매핑 정보만 상속할 superClass라는 의미. 다른 엔티티 클래스가 이 클래스를 상속하면 이 클래스의 필드들도 컬럼으로 추가한다.
@EntityListeners(...): 해당 클래스에 Auditing 기능을 포함한다.
@CreateDate: 생성 시점의 시각을 자동 저장한다.
@LastModifiedDate: 값 변경 시점의 시각을 자동 저장한다.
 */
