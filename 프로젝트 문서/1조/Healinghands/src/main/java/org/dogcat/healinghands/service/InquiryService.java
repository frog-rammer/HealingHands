package org.dogcat.healinghands.service;

import org.dogcat.healinghands.dto.InquiryDTO;
import org.dogcat.healinghands.dto.VolunteerDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface InquiryService {
    void saveInquiry(InquiryDTO inquiryDTO);
    List<InquiryDTO> getAllInquiries();
    InquiryDTO getInquiryById(Long id);
    void updateInquiry(Long id, InquiryDTO inquiryDTO);
    Page<InquiryDTO> getInquiry(Pageable pageable);
    Page<InquiryDTO> findByUserId(String userId, Pageable pageable);
    Page<InquiryDTO> searchInquiries(String keyword, Pageable pageable);
}
