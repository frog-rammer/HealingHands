package org.dogcat.healinghands.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder  // 빌더 패턴 사용
@NoArgsConstructor  // 기본 생성자 추가
@AllArgsConstructor  // 모든 필드를 받는 생성자 추가


public class InquiryDTO {
    private Long inquiryId;  // 문의 ID
    private String userId;   // 사용자의 userId
    private String title;    // 문의 제목
    private String type;     // 문의 유형
    private String content;  // 문의 내용
    private LocalDateTime createdAt;  // 생성 시간
}
