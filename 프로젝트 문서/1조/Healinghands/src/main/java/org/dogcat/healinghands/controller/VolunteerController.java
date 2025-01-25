package org.dogcat.healinghands.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.dogcat.healinghands.dto.ShelterDTO;
import org.dogcat.healinghands.dto.UserDTO;
import org.dogcat.healinghands.dto.VolunteerDTO;
import org.dogcat.healinghands.entity.Shelter;
import org.dogcat.healinghands.service.ShelterService;
import org.dogcat.healinghands.service.UserService;
import org.dogcat.healinghands.service.VolunteerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/volunteers")
public class VolunteerController {

    @Autowired
    private VolunteerService volunteerService;

    @Autowired
    private ShelterService shelterService;
    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public String getVolunteerList(@RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "12") int size,
                                   @RequestParam(value ="keyword", required = false) String keyword,
                                   Model model) {

        Pageable pageable = PageRequest.of(page, size);
        Page<VolunteerDTO> volunteerPage;

        // 검색어가 있을 경우와 없을 경우에 따라 봉사자 목록을 가져옴
        if (keyword != null && !keyword.isEmpty()) {
            volunteerPage = volunteerService.searchVolunteers(keyword, pageable);
        } else {
            volunteerPage = volunteerService.getVolunteer(pageable);
        }

        // volunteerPage가 비어있지 않으면 쉘터 정보를 추가
        if (!volunteerPage.isEmpty()) {
            ShelterDTO shelter = shelterService.getShelterById(volunteerPage.getContent().get(0).getShelterId());
            String shelterName = shelter.getShelterName();
            model.addAttribute("shelterName", shelterName);
        } else {
            model.addAttribute("shelterName", ""); // volunteerPage가 비어 있을 경우 기본값 설정
        }

        // 모델에 필요한 정보 추가
        model.addAttribute("volunteerPage", volunteerPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", volunteerPage.getTotalPages());

        return "volunteer";  // volunteer.html 페이지로 이동
    }


    @GetMapping("/service/register")
    public String serviceRegister(Model model, HttpSession session) {
        if(session.getAttribute("user") == null) {
            return  "redirect:/users/login" ;
        }
        model.addAttribute("volunteerDTO", new VolunteerDTO());
        return "serviceRegisterForm";
    }


    @PostMapping("/register")
    public String createVolunteer(@ModelAttribute VolunteerDTO volunteerDTO ,HttpServletRequest request) {
        // 세션에서 userId와 shelterId 값을 가져옴
        HttpSession session = request.getSession();
        UserDTO user = (UserDTO) session.getAttribute("user");
        // volunteerDTO에 userId와 shelterId 설정
        volunteerDTO.setUserId(user.getUserId());
        volunteerDTO.setShelterId(user.getUserId());
        // DB에 봉사 내역 저장 및,다음페이지 이동
        volunteerService.createVolunteer(volunteerDTO);
        return "redirect:/volunteers/list";
    }

    @GetMapping("/detail/{id}")
    public String getVolunteerDetail(HttpSession session,@PathVariable Long id, Model model) {
        if(session.getAttribute("user") == null) {
            return "redirect:/users/login";
        }

        VolunteerDTO volunteerDTO = volunteerService.getVolunteerById(id);
        model.addAttribute("volunteerDTO", volunteerDTO);  // 모델에 문의 정보 추가
        shelterService.getShelterById(volunteerDTO.getShelterId());
        UserDTO user = userService.getUserById(volunteerDTO.getUserId());
        ShelterDTO shelter = shelterService.getShelterById(user.getUserId());
        model.addAttribute("user", user);
        model.addAttribute("shelter", shelter);
        return "volunteerBoard";  //
    }

    @PostMapping("/approveApplicant")
    public ResponseEntity<String> approveApplicant(@RequestParam("userId") String userId,
                                                   @RequestParam("volunteerId") String volunteerIdStr) {
        try {
            Long volunteerId = Long.parseLong(volunteerIdStr);  // String을 Long으로 변환
            volunteerService.approveApplicant(volunteerId, userId);
            return ResponseEntity.ok("지원자가 승인되었습니다.");
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("잘못된 volunteerId 형식입니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("지원자 승인 중 오류가 발생했습니다.");
        }
    }

    @PostMapping("/rejectApplicant")
    public ResponseEntity<String> rejectApplicant(@RequestParam("userId") String userId,
                                                  @RequestParam("volunteerId") String volunteerIdStr) {
        try {
            Long volunteerId = Long.parseLong(volunteerIdStr);  // String을 Long으로 변환
            volunteerService.rejectApplicant(volunteerId, userId);
            return ResponseEntity.ok("지원자가 거부되었습니다.");
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("잘못된 volunteerId 형식입니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("지원자 거부 중 오류가 발생했습니다.");
        }
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        VolunteerDTO volunteer = volunteerService.getVolunteerById(id); // ID로 봉사활동 데이터 가져오기
        model.addAttribute("volunteerDTO", volunteer); // 모델에 봉사 DTO 데이터 추가
        return "serviceUpdateForm"; // serviceupdateform.html로 이동
    }

    @GetMapping("/app/{id}")
    public String applicantsUpdate(@PathVariable Long id,HttpSession session) {
        UserDTO user = (UserDTO) session.getAttribute("user");
        volunteerService.updateApplicants(id,user.getUserId());
        return "redirect:/volunteers/list";
    }


    @PostMapping("/update")
    public String updateVolunteer(@ModelAttribute VolunteerDTO volunteerDTO) {
        volunteerService.updateVolunteer(volunteerDTO.getVolunteerId(), volunteerDTO);
        return "redirect:/volunteers/list"; // 수정 후 봉사활동 목록 페이지로 리디렉션
    }

    @GetMapping("/{id}")
    public ResponseEntity<VolunteerDTO> getVolunteer(@PathVariable Long id) {
        VolunteerDTO volunteer = volunteerService.getVolunteerById(id);
        return ResponseEntity.ok(volunteer);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVolunteer(@PathVariable Long id) {
        volunteerService.deleteVolunteer(id);
        return ResponseEntity.noContent().build();
    }
}

