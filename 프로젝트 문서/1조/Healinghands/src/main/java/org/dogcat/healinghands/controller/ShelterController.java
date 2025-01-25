package org.dogcat.healinghands.controller;


import jakarta.servlet.http.HttpSession;
import org.dogcat.healinghands.dto.*;
import org.dogcat.healinghands.service.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/shelters")
public class ShelterController {


    @Autowired
    private ShelterService shelterService;
    @Autowired
    private UserService userService;
    @Autowired
    private VolunteerService volunteerService;
    @Autowired
    private AnimalService animalService;
    @Autowired
    private AdoptionService adoptionService;
    @Autowired
    private DonationService donationService;
    /*
     * 보호소 등록 페이지
     */
    @GetMapping("/register")
    public String shelterRegister(HttpSession session,Model model) {
        UserDTO user = (UserDTO) session.getAttribute("user");
        if(user == null) {
            return "redirect:/users/login";
        }else{
            model.addAttribute("shelterDTO", new ShelterDTO()); // 새로운 ShelterDTO 객체를 모델에 추가
            return "shelterregisterview"; // shelterregisterview.html 띄워주기
        }
    }
    /*
     * 보호소 등록 신청하기
     */
    @PostMapping("/register")
    public String createShelter(HttpSession session,
                                @ModelAttribute ShelterDTO shelterDTO,
                                @RequestParam("shelterPhoto") MultipartFile shelterPhoto, // 추가된 부분
                                RedirectAttributes redirectAttributes) {
        UserDTO user = (UserDTO) session.getAttribute("user");

        shelterDTO.setShelterId(user.getUserId());
        System.out.println(shelterDTO.getAddress());
        System.out.println(shelterDTO.getAddress());
        System.out.println(shelterDTO.getAddress());System.out.println(shelterDTO.getAddress());
        System.out.println(shelterDTO.getAddress());
        System.out.println(shelterDTO.getAddress());
        System.out.println(shelterDTO.getAddress());
        System.out.println(shelterDTO.getAddress());
        // ShelterService를 사용하여 보호소 등록
        ShelterDTO insertForWaitShelterApp = shelterService.insertWaitShelter(shelterDTO, shelterPhoto);

        // 성공 메시지 추가
        redirectAttributes.addFlashAttribute("message", "보호소가 성공적으로 등록되었습니다.");

        // 메인 페이지로 리다이렉트
        return "redirect:/"; // 메인 페이지로 리다이렉트
    }

    /*
     *   보호소 운영자 페이지
     */
    @GetMapping("/management")
    public String shelterManagement(HttpSession session,
                                    Model model,
                                    @RequestParam(defaultValue = "0") int page) {
        // 세션에서 사용자 정보 가져오기
        UserDTO user = (UserDTO) session.getAttribute("user");
        String shelterId = user.getUserId();

        // shelterId 출력
        System.out.println("=======================");
        System.out.println("Shelter ID: " + shelterId);
        System.out.println("=======================");

        // 보호소 정보 가져오기 및 모델에 추가
        ShelterDTO shelter = shelterService.getShelterById(shelterId);
        model.addAttribute("shelterDTO", shelter);

        // ShelterDTO 정보 출력
        System.out.println("=======================");
        System.out.println("ShelterDTO: ");
        System.out.println(shelter);
        System.out.println("=======================");

        //후원 금액 및 후원자 데이터 베이스 처리
        // 후원 금액 및 후원자 데이터 가져오기
        Pageable donationPageable = PageRequest.of(page, 5);
        List<DonationDTO> donationList = donationService.getDonationByShelterName(shelterId,donationPageable);
        model.addAttribute("donationList", donationList);


        // 입양 동물 신청자 리스트 페이징 처리
        Pageable pageable = PageRequest.of(page, 5); // 한 페이지에 5명씩 표시
        Page<AdoptionDTO> adoptionDTOS = adoptionService.getAdoptionByShelterId(shelterId, pageable);
        model.addAttribute("adoptionDTOS", adoptionDTOS);
        // 신청자 리스트에 포함된 모든 animalId 추출
        List<Long> animalIds = adoptionDTOS.getContent().stream()
                .map(AdoptionDTO::getAnimalId)
                .collect(Collectors.toList());

        System.out.println("Animal IDs in adoptionDTOS:");
        animalIds.forEach(id -> System.out.println("Animal ID: " + id));
        System.out.println("=======================");

        // animalIds를 기반으로 AnimalDTO 목록 가져오기
        List<AnimalDTO> animalDTOs = animalService.getAnimalsByIds(animalIds);
        // animalId를 키로 하고 "종 - 품종 '이름'" 형식의 문자열을 값으로 하는 맵 생성
        Map<Long, String> animalNamesMap = animalDTOs.stream()
                .collect(Collectors.toMap(
                        AnimalDTO::getAnimalId,
                        animal -> animal.getSpecies() + " - " + animal.getBreed() + " '" + animal.getName() + "'"
                ));
        model.addAttribute("animalNamesMap", animalNamesMap);  // animalNamesMap을 템플릿에 전달


        // 페이지 정보 추가
        int totalPages = Math.min(adoptionDTOS.getTotalPages(), 10); // 최대 10 페이지
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        //=============================================== 봉사활동 신청 현황 ================================================
        // 봉사활동 정보 가져오기 및 모델에 추가
        List<VolunteerDTO> volunteerDTOs = volunteerService.getVolunteersByShelterId(shelterId);
        model.addAttribute("volunteerUserListDTOs", volunteerDTOs);

        // VolunteerDTO 리스트 출력
        System.out.println("=======================");
        System.out.println("VolunteerDTOs for shelterId " + shelterId + ": ");
        volunteerDTOs.forEach(System.out::println);
        System.out.println("=======================");

        // 봉사활동을 종류별로 그룹화하여 volunteerByType에 저장
        Map<String, List<VolunteerDTO>> volunteerByType = new HashMap<>();
        for (VolunteerDTO volunteer : volunteerDTOs) {
            String type = volunteer.getVolunteerType();
            volunteerByType
                    .computeIfAbsent(type, k -> new ArrayList<>())
                    .add(volunteer);
        }
        model.addAttribute("volunteerByType", volunteerByType);

        // VolunteerByType 출력
        System.out.println("=======================");
        System.out.println("VolunteerByType: ");
        volunteerByType.forEach((type, volunteers) -> {
            System.out.println("Type: " + type + " -> Volunteers: " + volunteers);
        });
        System.out.println("=======================");

        // 봉사활동 ID별 신청자(UserDTO) 리스트 매핑을 위한 Map volunteerApplicantsUserMap 생성
        Map<Long, List<UserDTO>> volunteerApplicantsUserMap = new HashMap<>();

        // 신청자(UserDTO)와 해당 봉사활동 ID를 매핑하기 위한 Map applicantVolunteerMap 생성
        Map<UserDTO, Long> applicantVolunteerMap = new HashMap<>();

        // 봉사활동 목록(volunteerDTOs)에서 각 봉사활동에 대해 신청자 정보를 추출하고 매핑 작업을 진행합니다.
        for (VolunteerDTO volunteer : volunteerDTOs) {
            // 현재 봉사활동의 ID를 volunteerId 변수에 저장
            Long volunteerId = volunteer.getVolunteerId();

            // 현재 봉사활동에 지원한 신청자(UserDTO)들을 저장할 리스트를 초기화
            List<UserDTO> applicants = new ArrayList<>();

            // 현재 봉사활동에 신청자가 있는지 확인 (null이 아닐 때만 진행)
            if (volunteer.getVolunteerApplicants() != null) {
                // 신청자들의 ID 문자열을 ','로 분리하여 배열로 변환
                String[] applicantIds = volunteer.getVolunteerApplicants().split(",");

                // 분리된 신청자 ID 배열을 순회하면서 각 ID에 해당하는 신청자(UserDTO)를 조회 및 매핑 작업 수행
                for (String applicantId : applicantIds) {
                    // 각 신청자 ID를 UserService를 통해 UserDTO로 변환, applicant에 저장
                    UserDTO applicant = userService.getUserById(applicantId.trim());

                    // UserDTO 객체가 null이 아닌 경우 (유효한 신청자인 경우)만 리스트와 맵에 추가
                    if (applicant != null) {
                        // 신청자 리스트에 해당 UserDTO를 추가
                        applicants.add(applicant);

                        // applicantVolunteerMap에 신청자와 해당 봉사활동 ID 매핑
                        applicantVolunteerMap.put(applicant, volunteerId);
                    }
                }
            }

            // volunteerApplicantsUserMap에 봉사활동 ID와 해당 신청자 리스트를 매핑
            volunteerApplicantsUserMap.put(volunteerId, applicants);
        }
        model.addAttribute("volunteerApplicantsUserMap", volunteerApplicantsUserMap);
        model.addAttribute("applicantVolunteerMap", applicantVolunteerMap);

        // volunteerApplicantsUserMap 출력
        System.out.println("=======================");
        System.out.println("VolunteerApplicantsUserMap: ");
        volunteerApplicantsUserMap.forEach((volunteerId, applicants) -> {
            System.out.println("Volunteer ID: " + volunteerId + " -> Applicants: " + applicants);
        });
        System.out.println("=======================");

        // applicantVolunteerMap 출력
        System.out.println("=======================");
        System.out.println("ApplicantVolunteerMap: ");
        applicantVolunteerMap.forEach((applicant, volunteerId) -> {
            System.out.println("Applicant: " + applicant + " -> Volunteer ID: " + volunteerId);
        });
        System.out.println("=======================");

        // 모든 신청자 목록을 리스트로 생성
        List<UserDTO> allApplicants = new ArrayList<>(applicantVolunteerMap.keySet());
        model.addAttribute("allApplicants", allApplicants);

        // AllApplicants 출력
        System.out.println("=======================");
        System.out.println("AllApplicants: ");
        allApplicants.forEach(System.out::println);
        System.out.println("=======================");

        return "shelterDashboard";
    }



    @GetMapping("/shelters")
    public List<ShelterDTO> getAllShelters() {
        return shelterService.getAllShelters();
    }


    @GetMapping("/info/{id}")
    public String getShelter(@PathVariable("id") String id,Model model) {
        ShelterDTO shelter = shelterService.getShelterById(id);
        System.out.println("=====================" + shelter.getShelterId());
        model.addAttribute("shelter", shelter);

        return "shelterinfoview";
    }

    @GetMapping("/checkshelterName")
    @ResponseBody // JSON 응답을 반환하도록 설정
    public boolean checkshelterName(@RequestParam String shelterName) {
        return !shelterService.isshelterNameExists(shelterName);
    }

}
