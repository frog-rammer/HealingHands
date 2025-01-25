package org.dogcat.healinghands.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder  // 빌더 패턴 사용
@NoArgsConstructor  // 기본 생성자 추가
@AllArgsConstructor  // 모든 필드를 받는 생성자 추가
@Entity
@EntityListeners(AuditingEntityListener.class)
@ToString
public class Inquiry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inquiryId;  // 문의 ID (자동 생성)
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private String userId;  // User 엔터티와의 관계 설정 (userId와 매핑)

    private String title;  // 문의 제목
    private String type;   // 문의 유형 (입양, 보호소 신청 등)
    private String content;  // 문의 내용
    @CreationTimestamp
    private LocalDateTime createdAt;  // 생성 시간


}
