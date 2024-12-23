package org.dogcat.healinghands.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long animalId;
    @Column(name = "shelter_id")
    private String shelterId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "shelter_id", insertable = false, updatable = false)
    private Shelter shelter;

    private String name;
    private String species;
    private String breed;
    private int age;
    private double weight;
    private String color;
    private String gender;
    private LocalDate shelterDate = LocalDate.now(); // 기본값 설정
    private String status; // 기본값은 없지만 DTO에서 필수로 제공해야 함
    private boolean vaccinated;
    private boolean adoptable;
    private boolean neutered;
    private String description;
    private String imageUrl;
    private LocalDateTime createdAt; // 등록 시간



    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now(); // 현재 시간으로 설정
    }
}