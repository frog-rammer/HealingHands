package org.dogcat.healinghands.controller;

import jakarta.servlet.http.HttpSession;
import org.dogcat.healinghands.dto.DonationDTO;
import org.dogcat.healinghands.dto.ShelterDTO;
import org.dogcat.healinghands.dto.UserDTO;
import org.dogcat.healinghands.service.DonationService;
import org.dogcat.healinghands.service.ShelterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/donation")
public class DonationController {

    @Autowired
    private DonationService donationService;
    @Autowired
    private ShelterService shelterService;


    @GetMapping("/list")
    public String getDonationlist(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "12") int size,
                                  Model model) {

        Pageable pageable = PageRequest.of(page, size);
        Page<DonationDTO> donationPage = donationService.getDonation(pageable);


        model.addAttribute("donationPage",donationPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", donationPage.getTotalPages());

        return "donation1";  //
    }

    UserDTO userDTO;
    // 후원 신청 폼 페이지를 보여줌
    @GetMapping("/form")
    public String showDonationForm(HttpSession session, Model model, Principal principal) {

        UserDTO userDTO = (UserDTO) session.getAttribute("user");

        if (userDTO == null) {
            // 유저가 로그인하지 않은 상태라면 로그인 페이지로 리다이렉트
            return "redirect:/users/login";
        }

        DonationDTO donationDTO = DonationDTO.builder()
                .userId(userDTO.getUserId())      // 세션에서 가져온 유저 ID
                .userName(userDTO.getUsername())  // 세션에서 가져온 유저 이름
                .phone(userDTO.getPhone())        // 세션에서 가져온 유저 전화번호
                .email(userDTO.getEmail())        // 세션에서 가져온 유저 이메일
                .donatedAt(LocalDateTime.now())   // 현재 시간으로 기부 시간 설정
                .build();

        // ShelterDTOs 리스트 생성 및 모델에 추가
        List<ShelterDTO> shelterDTOs = shelterService.getAllShelters(); // shelterService를 통해 모든 보호소 정보 가져오기
        model.addAttribute("shelterDTOs", shelterDTOs);
        model.addAttribute("donationDTO", donationDTO);  // 모델에 DonationDTO 객체 추가

        return "donation2";  // donation2.html 템플릿으로 이동
    }
    @ResponseBody
    @PostMapping("/form/register")
    public ResponseEntity<Void> registerDonation(@RequestBody DonationDTO donationDTO) {
        System.out.println("registerDonation 호출됨: " + donationDTO);

        // 결제가 성공한 경우에만 후원 정보를 저장합니다.

        donationService.registerDonation(donationDTO);
        return ResponseEntity.ok().build();  // 성공 시 200 OK 응답

    }
}
