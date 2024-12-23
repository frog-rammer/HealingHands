package org.dogcat.healinghands.service;

import org.dogcat.healinghands.dto.DonationDTO;
import org.dogcat.healinghands.dto.InquiryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DonationService {
    void registerDonation(DonationDTO donationDTO);  // 후원 등록
    List<DonationDTO> getAllDonations();  // 모든 후원 내역 조회
    Page<DonationDTO> findByUserId(String userId, Pageable pageable);
    Page<DonationDTO> getDonation(Pageable pageable);
    List<DonationDTO> getDonationByShelterName(String shelterName, Pageable pageable);
}
