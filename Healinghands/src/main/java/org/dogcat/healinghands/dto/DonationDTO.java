package org.dogcat.healinghands.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor  // 기본 생성자 생성
@AllArgsConstructor // 모든 필드를 포함한 생성자 생성
public class DonationDTO {

    private Long donationId;
    private String userId;       // 로그인 사용자 ID
    private String userName;     // 사용자 이름
    private String phone;        // 연락처
    private String email;        // 이메일
    private String shelterName;  // 보호소 이름
    private Double amount;       // 후원 금액
    private String paymentMethod; // 결제 방법
    private String transactionId; // 거래 ID
    private String impUid;       // 아임포트 결제 고유 식별자
    private LocalDateTime donatedAt; // 기부 날짜

    // 특정 필드를 위한 생성자
    public DonationDTO(Long donationId, String userId, String shelterName, double amount, LocalDate localDate) {
        this.donationId = donationId;
        this.userId = userId;
        this.shelterName = shelterName;
        this.amount = amount;
        this.donatedAt = localDate.atStartOfDay(); // LocalDate를 LocalDateTime으로 변환
    }
}
