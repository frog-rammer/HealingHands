package org.zerock.mmh.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.mmh.dto.PageRequestDTO;
import org.zerock.mmh.dto.UserFavoriteDTO;
import org.zerock.mmh.service.favoriteService;

@Controller
@RequestMapping("/favorite")
@Log4j2
@RequiredArgsConstructor //자동 주입을 위한 Annotation
public class favoriteController {

    private final favoriteService service;

    @GetMapping("/")
    public String index() {
        return "redirect:/favorite";
    }

    @GetMapping("list")
    public void list(PageRequestDTO pageRequestDTO, Model model) {

        log.info("list..." + pageRequestDTO);

        model.addAttribute("result", service.getList(pageRequestDTO));
    }

    @GetMapping("/list")
    public void register() {
        log.info("register get...");
    }

    @PostMapping("/list")
    public String register(UserFavoriteDTO dto, RedirectAttributes redirectAttributes) {
        log.info("dto..." + dto);

        //새로 추가된 엔티티의 번호
        String userMemNo = service.register(dto);

        redirectAttributes.addFlashAttribute("userMemNo", userMemNo);

        return "redirect:/favorite/list";
    }

    @PostMapping("/bookmark")
    public String bookmark(@RequestParam String productNo, @RequestParam String userMemNo, RedirectAttributes redirectAttributes) {
        UserFavoriteDTO dto = UserFavoriteDTO.builder()
                .productNo(productNo)
                .userMemNo(userMemNo)
                .build();

        // 즐겨찾기 등록
        String result = service.register(dto);
        redirectAttributes.addFlashAttribute("userMemNo", result);

        log.info("Bookmark added successfully. Redirecting to favorites list.");
        return "redirect:/userFavorite/list"; // 목록 페이지로 리다이렉트
    }
}