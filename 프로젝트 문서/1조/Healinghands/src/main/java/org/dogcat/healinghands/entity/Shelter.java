package org.dogcat.healinghands.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Shelter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sno; // 기본 키

    @Column(unique = true, nullable = false)
    private String shelterId; // 보호소 ID

    private String shelterName; // 보호소 이름
    private String address; // 주소
    private String detailAddress; // 상세 주소
    private String contactInfo; // 연락처
    private String operatingHours; // 운영 시간
    private Integer animalCount; // 보호 동물 수

    @Lob
    private String services; // 보호소 소개

    private String shelterPhotoPath; // 보호소 사진 경로
}
