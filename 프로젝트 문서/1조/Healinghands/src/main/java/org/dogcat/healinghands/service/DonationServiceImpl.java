package org.dogcat.healinghands.service;


import org.dogcat.healinghands.dto.DonationDTO;
import org.dogcat.healinghands.dto.InquiryDTO;
import org.dogcat.healinghands.entity.Donation;
import org.dogcat.healinghands.entity.Inquiry;
import org.dogcat.healinghands.repository.DonationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DonationServiceImpl implements DonationService {

    @Autowired
    private DonationRepository donationRepository;

    // 후원 등록 처리
    @Override
    public void registerDonation(DonationDTO donationDTO) {
        Donation donation = Donation.builder()
                .userId(donationDTO.getUserId())
                .userName(donationDTO.getUserName())
                .phone(donationDTO.getPhone())
                .shelterName(donationDTO.getShelterName())
                .amount(donationDTO.getAmount())
                .paymentMethod(donationDTO.getPaymentMethod())
                .transactionId(donationDTO.getTransactionId())
                .donatedAt(LocalDateTime.now())  // 현재 시간을 기부 시간으로 설정
                .build();

        donationRepository.save(donation);  // 기부 정보를 저장
    }
    @Override
    public List<DonationDTO> getDonationByShelterName(String shelterName, Pageable pageable) {
        Page<Donation> donations = donationRepository.findByShelterName(shelterName, pageable);
        return donations.getContent().stream()
                .map(donation -> DonationDTO.builder()
                        .donationId(donation.getDonationId())
                        .userId(donation.getUserId())
                        .userName(donation.getUserName())
                        .shelterName(donation.getShelterName())
                        .amount(donation.getAmount())
                        .donatedAt(donation.getDonatedAt())
                        .build())
                .collect(Collectors.toList());
    }

    // 후원 내역 조회
    @Override
    public List<DonationDTO> getAllDonations() {
        return donationRepository.findAll().stream()
                .map(donation -> new DonationDTO(
                        donation.getDonationId(),
                        donation.getUserId(),
                        donation.getShelterName(),
                        donation.getAmount(),
                        donation.getDonatedAt().toLocalDate()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public Page<DonationDTO> getDonation(Pageable pageable) {
        Page<Donation> donationPage =  donationRepository.findAll(pageable);
        return donationPage.map(this::toDTO);
    }

    private DonationDTO toDTO(Donation donation) {
        return DonationDTO.builder()
                .donationId(donation.getDonationId())
                .userId(donation.getUserId())
                .userName(donation.getUserName())
                .phone(donation.getPhone())
                .shelterName(donation.getShelterName())
                .amount(donation.getAmount())
                .paymentMethod(donation.getPaymentMethod())
                .transactionId(donation.getTransactionId())
                .donatedAt(donation.getDonatedAt())
                .build();
    }


    public Page<DonationDTO> findByUserId(String userId, Pageable pageable) {
        // Inquiry 엔티티 페이지를 DTO 페이지로 매핑하여 반환
        return donationRepository.findByUserId(userId, pageable)
                .map(this::toDTO);
    }
}
