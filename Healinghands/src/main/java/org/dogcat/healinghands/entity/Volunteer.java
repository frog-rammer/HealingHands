package org.dogcat.healinghands.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Volunteer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long volunteerId;  // 봉사활동 ID, Primary Key

    private String userId;  // 사용자 ID

    @Column(name = "shelter_id") // shelter_id 컬럼에 직접 매핑
    private String shelterId;  // 보호소 ID (DB에 쓰기 가능)

    @ManyToOne
    @JoinColumn(name = "shelter_id", referencedColumnName = "shelterId", insertable = false, updatable = false)
    private Shelter shelter; // Shelter와의 관계 설정 (읽기 전용)

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
