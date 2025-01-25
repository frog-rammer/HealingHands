package org.dogcat.healinghands.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {
    @Id
    private String userId;
    private String username;
    private String userAddress;
    private String password;
    private String email;
    private String phone;
    private LocalDate birthdate;
    private String userType = "USER";
    private Double donationAmount = 0.0;
    private String adoptionStatus= "없음";
    private LocalDate creationDate = LocalDate.now();
}
