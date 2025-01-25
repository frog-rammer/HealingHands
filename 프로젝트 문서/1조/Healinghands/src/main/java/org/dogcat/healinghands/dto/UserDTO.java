package org.dogcat.healinghands.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String userId;
    private String username;
    private String userAddress;
    private String userType ="USER";
    private String password;
    private String email;
    private String phone;
    private LocalDate birthdate;
    private Double donationAmount = 0.0;
    private String adoptionStatus= "없음";
    private LocalDate creationDate = LocalDate.now();
}
