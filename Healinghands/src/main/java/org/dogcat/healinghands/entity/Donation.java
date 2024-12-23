package org.dogcat.healinghands.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor  // 기본 생성자 자동 생성
@AllArgsConstructor // 모든 필드를 포함한 생성자 자동 생성
public class Donation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long donationId;

    private String userId;
    private String userName;
    private String phone;
    private String email;
    private String shelterName;
    private double amount;
    private String paymentMethod;
    private String transactionId;
    private String impUid;
    private LocalDateTime donatedAt;
}