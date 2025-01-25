package org.dogcat.healinghands.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdoptionDTO {
    private String userId;            // 사용자 ID
    private String shelterId;       // 보호소 ID
    private Long animalId;
    private String name;          // 성명
    private String contact;       // 연락처
    private String email;         // 이메일주소
    private String gender;        // 성별
    private int age;              // 나이
    private String address;       // 집주소
    private boolean consent;      // 개인정보 수집 및 이용 동의
    private String adoptionQA;    // 입양 관련 Q&A (1개의 QA contents)
}
