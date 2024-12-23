package org.dogcat.healinghands.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.dogcat.healinghands.dto.AnimalDTO;
import org.dogcat.healinghands.dto.ShelterDTO;
import org.dogcat.healinghands.dto.UserDTO;
import org.dogcat.healinghands.service.AnimalService;
import org.dogcat.healinghands.service.DailyVisitorService;
import org.dogcat.healinghands.service.ShelterService;
import org.dogcat.healinghands.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private  ShelterService shelterService;

    @Autowired
    private DailyVisitorService dailyVisitorService;

    @Autowired
    private AnimalService  animalService;

    /*
     * 메인 페이지 띄우기
     */
    @GetMapping("/")
    public String showHomePage(
            Model model,
            @RequestParam(defaultValue = "0") int page, // 페이지 번호 (0부터 시작)
            @RequestParam(defaultValue = "12") int size, // 한 페이지당 항목 수
            HttpServletRequest request
    ) {
        // 최근 등록된 동물 5마리를 가져옴
        List<AnimalDTO> recentAnimals = animalService.getRecentAnimals(5);
        model.addAttribute("recentAnimals", recentAnimals);

        // IP 주소 가져오기
        String clientIp = request.getRemoteAddr();

        // 방문자 수 증가
        dailyVisitorService.incrementVisitorCount(clientIp);

        // 페이지네이션된 보호소 리스트 가져오기
        Pageable pageable = PageRequest.of(page, size);
        Page<ShelterDTO> shelterPage = shelterService.getAllSheltersPageable(pageable);

        model.addAttribute("shelters", shelterPage.getContent()); // 현재 페이지의 보호소 리스트
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", shelterPage.getTotalPages());

        return "index";
    }

}
