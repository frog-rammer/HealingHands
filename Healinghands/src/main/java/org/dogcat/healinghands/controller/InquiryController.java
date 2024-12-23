package org.dogcat.healinghands.controller;

import jakarta.servlet.http.HttpSession;
import org.dogcat.healinghands.dto.InquiryDTO;
import org.dogcat.healinghands.dto.UserDTO;
import org.dogcat.healinghands.service.InquiryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/inquiry")
public class InquiryController {

    @Autowired
    private InquiryService inquiryService;

    @GetMapping("/list")
    public String getInquiryList(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "12") int size,
                                 @RequestParam(value = "keyword", required = false) String keyword,
                                 Model model) {

        Pageable pageable = PageRequest.of(page, size);
        Page<InquiryDTO> inquiryPage;

        // 검색어가 있을 경우 검색 결과 반환, 없으면 전체 목록 반환
        if (keyword != null && !keyword.isEmpty()) {
            inquiryPage = inquiryService.searchInquiries(keyword, pageable);
        } else {
            inquiryPage = inquiryService.getInquiry(pageable);
        }

        model.addAttribute("inquiryPage", inquiryPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", inquiryPage.getTotalPages());
        model.addAttribute("pageSize", size);
        model.addAttribute("keyword", keyword);  // 검색어 추가

        return "qna";  // qna.html 페이지로 이동
    }


    // 문의 등록 폼 페이지로 이동
    @GetMapping("/new")
    public String showInquiryForm(HttpSession session, Model model) {
        if(session.getAttribute("user") == null) {
            return "redirect:/users/login";
        }
        model.addAttribute("inquiryDTO", new InquiryDTO());  // 빈 InquiryDTO 객체 전달
        return "qna2";  // 문의 등록 폼 페이지 (qna2.html)로 이동
    }

    // 문의 등록 처리
    @PostMapping("/save")
    public String saveInquiry(HttpSession session, @ModelAttribute InquiryDTO inquiryDTO) {
        System.out.println("메서드 실행=====================================================");
        UserDTO user = (UserDTO)session.getAttribute("user");

        inquiryDTO.setUserId(user.getUserId());

        inquiryService.saveInquiry(inquiryDTO);  // 문의 저장

        return "redirect:/inquiry/list";  // 등록 후 Q&A 게시판으로 리다이렉트
    }

    // 특정 문의의 상세 정보 페이지로 이동
    @GetMapping("/detail/{id}")
    public String getInquiryDetail(@PathVariable Long id, Model model) {
        InquiryDTO inquiryDTO = inquiryService.getInquiryById(id);
        model.addAttribute("inquiryDTO", inquiryDTO);  // 모델에 문의 정보 추가
        return "qna3";  // 문의 상세 페이지 (qnaDetail.html)로 이동
    }

    // 수정 폼 페이지로 이동
    @GetMapping("/edit/{id}")
    public String editInquiry(@PathVariable Long id, Model model) {
        InquiryDTO inquiryDTO = inquiryService.getInquiryById(id);
        model.addAttribute("inquiryDTO", inquiryDTO);
        System.out.println("=================================" + inquiryDTO.getInquiryId()+"==================================================================");
        return "qnamodify";  // 수정 페이지로 이동 (등록 페이지와 분리된 파일명인지 확인 필요)
    }

    // 수정 작업 수행 후 목록으로 리다이렉트
    @PostMapping("/edit2/{id}")
    public String updateInquiry(@PathVariable Long id, @ModelAttribute InquiryDTO inquiryDTO) {
        inquiryService.updateInquiry(id, inquiryDTO);  // 수정된 정보 저장
        return "redirect:/inquiry/list";  // 수정 후 목록 페이지로 리다이렉트
    }
}
