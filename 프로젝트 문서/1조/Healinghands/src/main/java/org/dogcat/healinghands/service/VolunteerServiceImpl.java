package org.dogcat.healinghands.service;

import org.dogcat.healinghands.dto.InquiryDTO;
import org.dogcat.healinghands.dto.VolunteerDTO;
import org.dogcat.healinghands.entity.Inquiry;
import org.dogcat.healinghands.entity.Volunteer;
import org.dogcat.healinghands.repository.ShelterRepository;
import org.dogcat.healinghands.repository.UserRepository;
import org.dogcat.healinghands.repository.VolunteerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VolunteerServiceImpl implements VolunteerService {


    @Autowired
    private VolunteerRepository volunteerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ShelterRepository shelterRepository;

    @Override
    public VolunteerDTO createVolunteer(VolunteerDTO volunteerDTO) {
        Volunteer volunteer = convertToEntity(volunteerDTO);
        Volunteer savedVolunteer = volunteerRepository.save(volunteer);
        return convertToDTO(savedVolunteer);
    }

    @Override
    public VolunteerDTO getVolunteerById(Long id) {
        // ID로 VolunteerApplication 엔티티 조회
        Volunteer volunteer = volunteerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Volunteer not found with id: " + id));

        // VolunteerDTO로 변환하여 반환
        return VolunteerDTO.builder()
                .volunteerId(volunteer.getVolunteerId())
                .userId(volunteer.getUserId())
                .shelterId(volunteer.getShelterId())
                .title(volunteer.getTitle())
                .description(volunteer.getDescription())
                .scheduledDate(volunteer.getScheduledDate())
                .peopleLimit(volunteer.getPeopleLimit())
                .volunteerType(volunteer.getVolunteerType())
                .build();
    }

    @Override
    public List<VolunteerDTO> getAllVolunteers(){
        return volunteerRepository.findAll().stream()
                .map(volunteer -> VolunteerDTO.builder()
                        .volunteerId(volunteer.getVolunteerId())
                        .userId(volunteer.getUserId())
                        .shelterId(volunteer.getShelterId())
                        .title(volunteer.getTitle())
                        .description(volunteer.getDescription())
                        .scheduledDate(volunteer.getScheduledDate())
                        .peopleLimit(volunteer.getPeopleLimit())
                        .volunteerType(volunteer.getVolunteerType())
                        .build()
                )
                .collect(Collectors.toList());
    }
    @Override
    public Page<VolunteerDTO> getVolunteer(Pageable pageable) {
        Page<Volunteer> volunteerPage = volunteerRepository.findAll(pageable);
        return volunteerPage.map(this::volunteerEntityToDTO);
    }

    @Override
    public Page<VolunteerDTO> searchVolunteers(String keyword, Pageable pageable) {
        Page<Volunteer> volunteerPage = volunteerRepository.findByTitleContainingOrShelterNameContaining(keyword,keyword,pageable);
        return volunteerPage.map(this::volunteerEntityToDTO);
    }

    @Override
    public VolunteerDTO updateVolunteer(Long id, VolunteerDTO volunteerDTO) {
        Volunteer volunteer = volunteerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Volunteer not found with id: " + id));

        // 전달된 volunteerDTO 데이터를 이용해 엔티티 값을 업데이트
        volunteer.setScheduledDate(volunteerDTO.getScheduledDate());
        volunteer.setTitle(volunteerDTO.getTitle());
        volunteer.setDescription(volunteerDTO.getDescription());
        volunteer.setPeopleLimit(volunteerDTO.getPeopleLimit());
        volunteer.setVolunteerType(volunteerDTO.getVolunteerType());

        // 변경된 엔티티를 저장
        Volunteer updatedVolunteer = volunteerRepository.save(volunteer);

        // 엔티티를 DTO로 변환하여 반환
        return convertToDTO(updatedVolunteer);
    }

    @Override
    @Transactional
    public void updateApplicants(Long volunteerId, String userId) {
        // 봉사활동 ID로 Volunteer 객체 조회
        Volunteer volunteer = volunteerRepository.findById(volunteerId)
                .orElseThrow(() -> new IllegalArgumentException("Volunteer not found with id: " + volunteerId));

        // 기존 신청자 리스트 가져오기
        String applicants = volunteer.getVolunteerApplicants();

        // 신청자 목록이 이미 있는 경우 확인
        if (applicants != null && !applicants.isEmpty()) {
            // 기존 신청자 목록을 배열로 분리하여 중복 여부 확인
            String[] applicantArray = applicants.split(",");
            for (String applicant : applicantArray) {
                if (applicant.trim().equals(userId)) {
                    // 중복된 ID가 있으면 추가하지 않고 종료
                    return;
                }
            }
            // 중복되지 않은 경우 새 ID 추가
            volunteer.setVolunteerApplicants(applicants + "," + userId);
        } else {
            // 신청자 목록이 비어 있는 경우, 바로 userId 설정
            volunteer.setVolunteerApplicants(userId);
        }

        // 변경 사항을 저장
        volunteerRepository.save(volunteer);
    }

    @Override
    public void deleteVolunteer(Long id) {
    }

    @Override
    public List<VolunteerDTO> getVolunteersByShelterId(String shelterId) {
        return volunteerRepository.findByShelterId(shelterId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    @Override
    public void approveApplicant(Long volunteerId, String userId) {
        Volunteer volunteer = volunteerRepository.findById(volunteerId)
                .orElseThrow(() -> new RuntimeException("봉사활동을 찾을 수 없습니다."));

        // 지원자를 승인 목록에 추가
        String approvedList = volunteer.getVolunteerList();
        approvedList = (approvedList == null ? "" : approvedList + ",") + userId;
        volunteer.setVolunteerList(approvedList);

        // 신청자 목록에서 해당 지원자를 제거
        String updatedApplicants = Arrays.stream(volunteer.getVolunteerApplicants().split(","))
                .filter(id -> !id.trim().equals(userId))
                .collect(Collectors.joining(","));

        // 모집 인원을 1 감소시키고, 0 이하가 되면 신청자 리스트 비움
        if (volunteer.getPeopleLimit() != null && volunteer.getPeopleLimit() > 0) {
            volunteer.setPeopleLimit(volunteer.getPeopleLimit() - 1);
            if (volunteer.getPeopleLimit() == 0) {
                volunteer.setVolunteerApplicants(null); // 모집 인원이 0이면 신청자 리스트 비움
            }
        }

        // 신청자 목록이 빈 문자열이면 null로 설정
        volunteer.setVolunteerApplicants(updatedApplicants.isEmpty() ? null : updatedApplicants);

        volunteerRepository.save(volunteer);
    }

    @Override
    public void rejectApplicant(Long volunteerId, String userId) {
        Volunteer volunteer = volunteerRepository.findById(volunteerId)
                .orElseThrow(() -> new RuntimeException("봉사활동을 찾을 수 없습니다."));

        // 신청자 목록에서 해당 지원자를 제거
        String updatedApplicants = Arrays.stream(volunteer.getVolunteerApplicants().split(","))
                .filter(id -> !id.trim().equals(userId))
                .collect(Collectors.joining(","));

        // 신청자 목록이 빈 문자열이면 null로 설정
        volunteer.setVolunteerApplicants(updatedApplicants.isEmpty() ? null : updatedApplicants);

        volunteerRepository.save(volunteer);
    }


    // Volunteer 엔티티를 VolunteerDTO로 변환
    public VolunteerDTO volunteerEntityToDTO(Volunteer volunteerApplication) {
        return VolunteerDTO.builder()
                .volunteerId(volunteerApplication.getVolunteerId())
                .userId(volunteerApplication.getUserId())
                .shelterId(volunteerApplication.getShelterId())
                .title(volunteerApplication.getTitle())
                .description(volunteerApplication.getDescription())
                .scheduledDate(volunteerApplication.getScheduledDate())
                .peopleLimit(volunteerApplication.getPeopleLimit())
                .volunteerType(volunteerApplication.getVolunteerType())
                .volunteerApplicants(volunteerApplication.getVolunteerApplicants()) // 신청자 리스트 매핑
                .volunteerList(volunteerApplication.getVolunteerList()) // 승인된 봉사자 리스트 매핑
                .build();
    }

    // VolunteerDTO를 Volunteer 엔티티로 변환
    private Volunteer convertToEntity(VolunteerDTO volunteerDTO) {
        if (volunteerDTO.getUserId() == null || volunteerDTO.getShelterId() == null) {
            throw new IllegalArgumentException("User ID and Shelter ID must not be null");
        }

        return Volunteer.builder()
                .volunteerId(volunteerDTO.getVolunteerId()) // 봉사활동 ID
                .userId(volunteerDTO.getUserId()) // 사용자 ID
                .shelterId(volunteerDTO.getShelterId()) // 보호소 ID
                .title(volunteerDTO.getTitle()) // 봉사활동 제목
                .description(volunteerDTO.getDescription()) // 봉사활동 설명
                .scheduledDate(volunteerDTO.getScheduledDate()) // 봉사 날짜
                .peopleLimit(volunteerDTO.getPeopleLimit()) // 모집 인원
                .volunteerType(volunteerDTO.getVolunteerType()) // 봉사활동 종류
                .volunteerApplicants(volunteerDTO.getVolunteerApplicants()) // 신청자 리스트 매핑
                .volunteerList(volunteerDTO.getVolunteerList()) // 승인된 봉사자 리스트 매핑
                .build();
    }

    // Volunteer 엔티티를 VolunteerDTO로 변환 (Builder 사용)
    private VolunteerDTO convertToDTO(Volunteer volunteer) {
        VolunteerDTO.VolunteerDTOBuilder builder = VolunteerDTO.builder()
                .volunteerId(volunteer.getVolunteerId())
                .scheduledDate(volunteer.getScheduledDate())
                .title(volunteer.getTitle())
                .description(volunteer.getDescription())
                .peopleLimit(volunteer.getPeopleLimit())
                .volunteerType(volunteer.getVolunteerType())
                .volunteerApplicants(volunteer.getVolunteerApplicants()) // 신청자 리스트 매핑
                .volunteerList(volunteer.getVolunteerList()); // 승인된 봉사자 리스트 매핑

        // User와 Shelter ID가 있는지 확인하여 추가
        if (volunteer.getUserId() != null) {
            builder.userId(volunteer.getUserId());
        }
        if (volunteer.getShelterId() != null) {
            builder.shelterId(volunteer.getShelterId());
        }

        return builder.build();
    }

}
