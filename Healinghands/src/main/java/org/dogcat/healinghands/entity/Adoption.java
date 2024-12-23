package org.dogcat.healinghands.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Adoption{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long applicationId;
    private Long animalId;
    private String userId;            // 사용자 ID
    private String shelterId;         // 보호소 ID
    private String name;
    private String contact;
    private String email;
    private String gender;
    private int age;
    private String address;
    private boolean consent;
    private String adoptionQA;

    // 필요한 추가 필드가 있을 경우 여기에 추가
}
