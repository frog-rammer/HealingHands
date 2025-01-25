package org.dogcat.healinghands.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VolunteerDTO {
    private Long volunteerId;  // 봉사활동 ID, Primary Key
    private String userId;  // 사용자 ID
    private String shelterId;  // 보호소 ID
    private String title;  // 봉사활동 제목
    private String description;  // 봉사활동 설명
    private String scheduledDate;  // 봉사 날짜
    private Integer peopleLimit;  // 모집 인원
    private String volunteerType;  // 봉사활동 종류
    private String volunteerApplicants; // 봉사활동 신청자들
    private String volunteerList; // 승인된 봉사 활동자 들
    private String shelterName;
    private String volunteerAddress;
}
