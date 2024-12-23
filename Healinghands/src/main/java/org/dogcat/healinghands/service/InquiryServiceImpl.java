package org.dogcat.healinghands.service;

import org.dogcat.healinghands.dto.InquiryDTO;
import org.dogcat.healinghands.entity.Inquiry;
import org.dogcat.healinghands.repository.InquiryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InquiryServiceImpl implements InquiryService {

    @Autowired
    private InquiryRepository inquiryRepository;

    @Override
    public void saveInquiry(InquiryDTO inquiryDTO) {
        Inquiry inquiry = Inquiry.builder()
                .userId(inquiryDTO.getUserId())
                .title(inquiryDTO.getTitle())
                .type(inquiryDTO.getType())
                .content(inquiryDTO.getContent())
                // .createdAt는 JPA가 자동으로 설정하므로 설정하지 않습니다.
                .build();
        Inquiry savedInquiry = inquiryRepository.save(inquiry);
    }
    @Override
    public void updateInquiry(Long id, InquiryDTO inquiryDTO) {
        Inquiry inquiry = inquiryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Inquiry not found with id: " + id));

        // DTO에서 수정된 내용을 엔티티에 설정
        inquiry.setTitle(inquiryDTO.getTitle());
        inquiry.setType(inquiryDTO.getType());
        inquiry.setContent(inquiryDTO.getContent());

        Inquiry editInquriry = inquiryRepository.save(inquiry);  // 수정된 엔티티 저장
    }
    @Override
    public List<InquiryDTO> getAllInquiries() {
        return inquiryRepository.findAll().stream().map(inquiry -> InquiryDTO.builder()
                .inquiryId(inquiry.getInquiryId())  // inquiryId 추가
                .userId(inquiry.getUserId())
                .title(inquiry.getTitle())
                .type(inquiry.getType())
                .content(inquiry.getContent())
                .createdAt(inquiry.getCreatedAt())
                .build()).collect(Collectors.toList());
    }
    @Override
    public Page<InquiryDTO> getInquiry(Pageable pageable) {
        Page<Inquiry> inquiryPage = inquiryRepository.findAll(pageable);
        return inquiryPage.map(this::inquiryEntityToDTO);
    }
    @Override
    public Page<InquiryDTO> searchInquiries(String keyword, Pageable pageable) {
        Page<Inquiry> inquiryPage = inquiryRepository.findByTitleContainingOrUserIdContaining(keyword, keyword, pageable);
        return inquiryPage.map(this::inquiryEntityToDTO);
    }

    // Entity to DTO 변환 메서드 추가
    private InquiryDTO inquiryEntityToDTO(Inquiry inquiry) {
        return InquiryDTO.builder()
                .inquiryId(inquiry.getInquiryId())
                .userId(inquiry.getUserId())
                .title(inquiry.getTitle())
                .type(inquiry.getType())
                .content(inquiry.getContent())
                .createdAt(inquiry.getCreatedAt())
                .build();
    }

    @Override
    public InquiryDTO getInquiryById(Long id) {
        Inquiry inquiry = inquiryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Inquiry not found with id: " + id));
        return InquiryDTO.builder()
                .inquiryId(inquiry.getInquiryId())  // inquiryId 추가
                .userId(inquiry.getUserId())
                .title(inquiry.getTitle())
                .type(inquiry.getType())
                .content(inquiry.getContent())
                .createdAt(inquiry.getCreatedAt())
                .build();
    }

    @Override
    public Page<InquiryDTO> findByUserId(String userId, Pageable pageable) {
        // Inquiry 엔티티 페이지를 DTO 페이지로 매핑하여 반환
        return inquiryRepository.findByUserId(userId, pageable)
                .map(this::toDTO);
    }
    private InquiryDTO toDTO(Inquiry inquiry) {
        return InquiryDTO.builder()
                .inquiryId(inquiry.getInquiryId())
                .userId(inquiry.getUserId())
                .title(inquiry.getTitle())
                .type(inquiry.getType())
                .content(inquiry.getContent())
                .createdAt(inquiry.getCreatedAt())
                .build();
    }

}
