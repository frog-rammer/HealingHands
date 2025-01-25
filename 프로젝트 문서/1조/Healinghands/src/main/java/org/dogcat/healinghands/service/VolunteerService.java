package org.dogcat.healinghands.service;

import org.dogcat.healinghands.dto.AnimalDTO;
import org.dogcat.healinghands.dto.InquiryDTO;
import org.dogcat.healinghands.dto.VolunteerDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface VolunteerService {
    VolunteerDTO createVolunteer(VolunteerDTO volunteerDTO);
    VolunteerDTO getVolunteerById(Long id); //
    List<VolunteerDTO> getAllVolunteers();
    VolunteerDTO updateVolunteer(Long id, VolunteerDTO volunteerDTO); //
    void deleteVolunteer(Long id); // ID를 String으로 변경
    Page<VolunteerDTO> getVolunteer(Pageable pageable);
    List<VolunteerDTO> getVolunteersByShelterId(String shelterId); // shelterId로 검색하는 메서드 추가
    void updateApplicants(Long volunteerId, String userId);
    void approveApplicant(Long volunteerId, String userId);
    void rejectApplicant(Long volunteerId, String userId);
    Page<VolunteerDTO> searchVolunteers(String keyword, Pageable pageable);
}
