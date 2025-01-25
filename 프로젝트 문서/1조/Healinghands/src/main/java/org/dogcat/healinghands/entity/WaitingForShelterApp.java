package org.dogcat.healinghands.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class WaitingForShelterApp {
    @Id
    private String shelterId; // 보호소 ID

    private String shelterName; // 보호소 이름
    private String address; // 주소
    private String contactInfo; // 연락처
    private String operatingHours; // 운영 시간
    private int animalCount; // 보호 동물 수
    private String introduction; //보호소 소개
    private String shelterPhotoPath; // 보호소 사진 경로

}





