package org.dogcat.healinghands.controller;

import jakarta.servlet.http.HttpSession;
import org.dogcat.healinghands.dto.DonationDTO;
import org.dogcat.healinghands.dto.InquiryDTO;
import org.dogcat.healinghands.dto.UserDTO;
import org.dogcat.healinghands.service.DonationService;
import org.dogcat.healinghands.service.InquiryService;
import org.dogcat.healinghands.service.UserService;
import org.dogcat.healinghands.service.VolunteerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/myPage")
public class MyPageController{


    @Autowired
    private InquiryService inquiryService;

    @Autowired
    private VolunteerService volunteerService;

    @Autowired
    UserService userService;

    @Autowired
    DonationService donationService;

    @GetMapping("/list")
    public String myPage(@RequestParam(defaultValue = "0") int page,
                         @RequestParam(defaultValue = "5") int size,
                         Model model, HttpSession session) {

        UserDTO currentUser = (UserDTO) session.getAttribute("user");
        if (currentUser == null) {
            return "redirect:/login"; // 로그인 상태가 아닐 경우 로그인 페이지로 리다이렉트
        }

        String currentUserId = currentUser.getUserId();
        Pageable pageable = PageRequest.of(page, size);

        // 사용자 정보
        UserDTO userInfo = userService.getUserById(currentUser.getUserId());
        model.addAttribute("my", userInfo);

        // 문의 내역과 후원 내역 페이지네이션 분리
        Page<InquiryDTO> inquiryPage = inquiryService.findByUserId(currentUserId, pageable);
        Page<DonationDTO> donationPage = donationService.findByUserId(currentUserId, pageable);

        model.addAttribute("inquiryPage2", inquiryPage);
        model.addAttribute("donationPage2", donationPage);

        // 각각의 페이지에 필요한 페이지네이션 정보 전달
        model.addAttribute("inquiryCurrentPage", page);
        model.addAttribute("inquiryTotalPages", inquiryPage.getTotalPages());

        model.addAttribute("donationCurrentPage", page);
        model.addAttribute("donationTotalPages", donationPage.getTotalPages());

        return "mypageview"; // 마이페이지 템플릿 이름
    }

}