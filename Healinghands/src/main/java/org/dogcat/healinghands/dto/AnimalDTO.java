package org.dogcat.healinghands.dto;

import lombok.*;

import org.dogcat.healinghands.entity.Animal;

import java.time.LocalDate;

@Getter
@Setter
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnimalDTO {
    private Long animalId; // 동물 ID
    private String name; // 동물 이름
    private String species; // 동물 종 (예: 개, 고양이)
    private String breed; // 품종
    private int age; // 나이
    private double weight; // 체중
    private String color; // 모색
    private String gender; // 성별 (예: "MALE" 또는 "FEMALE")
    private LocalDate shelterDate; // LocalDate로 변경
    private String status; // 동물 상태 (예: 입양 가능, 보호 중 등)
    private boolean vaccinated; // 예방접종 여부 (예/아니요)
    private boolean adoptable; // 입양 가능 여부 (예/아니요)
    private boolean neutered; // 중성화 여부 (예/아니요)
    private String description; // 상세 설명
    private String imageUrl; // 이미지 URL을 저장할 필드 추가
    private String shelterId; // 추가된 필드
}

