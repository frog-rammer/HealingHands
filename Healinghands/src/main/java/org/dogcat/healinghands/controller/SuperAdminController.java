package org.dogcat.healinghands.controller;

import jakarta.servlet.http.HttpSession;
import org.dogcat.healinghands.dto.ShelterDTO;
import org.dogcat.healinghands.dto.UserDTO;
import org.dogcat.healinghands.service.DailyVisitorService;
import org.dogcat.healinghands.service.ShelterService;
import org.dogcat.healinghands.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/superadmin")
public class SuperAdminController {

    @Autowired
    private ShelterService shelterService;
    @Autowired
    private DailyVisitorService dailyVisitorService;
    @Autowired
    private UserService userService;

    @GetMapping("/admin/dashboard")
    public String getDashboard(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "5") int size,
                               @RequestParam(required = false) String shelterQuery,
                               @RequestParam(required = false) String waitingShelterQuery,
                               @RequestParam(required = false) String userQuery,
                               Model model,
                               HttpSession session) {
        // 세션 체크
        UserDTO user = (UserDTO) session.getAttribute("user");
        if(user == null) {
            return "redirect:/";
        }else if(!user.getUserType().equals("ADMIN")){
            return "redirect:/";
        }

        Pageable pageable = PageRequest.of(page, size);

        // 기존 보호소 리스트 검색 기능
        Page<ShelterDTO> shelterPage;
        if (shelterQuery != null && !shelterQuery.isEmpty()) {
            shelterPage = shelterService.searchShelters(shelterQuery, pageable);
        } else {
            shelterPage = shelterService.getAllSheltersPageable(pageable);
        }

        // 보호소 신청자 리스트 검색 기능
        Page<ShelterDTO> waitingShelterPage;
        if (waitingShelterQuery != null && !waitingShelterQuery.isEmpty()) {
            waitingShelterPage = shelterService.searchWaitingShelters(waitingShelterQuery, pageable);
        } else {
            waitingShelterPage = shelterService.getAllWaitingSheltersPageable(pageable);
        }

        // 가입자 리스트 검색 기능
        Page<UserDTO> userPage;
        if (userQuery != null && !userQuery.isEmpty()) {
            userPage = userService.searchUserList(userQuery, pageable);
        } else {
            userPage = userService.getAllUsersPageable(pageable);
        }

        // 일별 방문자 수 데이터 추가 (해당 달의 마지막 날짜 계산)
        YearMonth currentYearMonth = YearMonth.now();
        int lastDayOfMonth = currentYearMonth.lengthOfMonth();
        List<Integer> dailyVisitors = IntStream.rangeClosed(1, lastDayOfMonth)
                .mapToObj(day -> {
                    LocalDate date = currentYearMonth.atDay(day);
                    return dailyVisitorService.getVisitorCount(date);
                })
                .toList();


        // 보호소 리스트 관련 데이터 추가
        model.addAttribute("shelterList", shelterPage.getContent());
        model.addAttribute("totalPages", shelterPage.getTotalPages());
        model.addAttribute("shelterQuery", shelterQuery);

        // 보호소 신청자 리스트 관련 데이터 추가
        model.addAttribute("waitingShelterList", waitingShelterPage.getContent());
        model.addAttribute("waitingShelterTotalPages", waitingShelterPage.getTotalPages());
        model.addAttribute("waitingShelterQuery", waitingShelterQuery);

        // 가입자 리스트 관련 데이터 추가
        model.addAttribute("userList", userPage.getContent()); // 페이징된 가입자 리스트 사용
        model.addAttribute("userTotalPages", userPage.getTotalPages()); // 사용자 리스트 페이징 추가
        model.addAttribute("userQuery", userQuery);

        // 일별 방문자 수 데이터 추가
        model.addAttribute("dailyVisitors", dailyVisitors);
        return "superAdmin/superAdmin";
    }

    @GetMapping("/api/visitors")
    @ResponseBody
    public List<Integer> getVisitorsByMonth(@RequestParam int month) {
        // 요청된 달의 마지막 날짜를 가져옵니다.
        YearMonth yearMonth = YearMonth.of(LocalDate.now().getYear(), month);
        int lastDayOfMonth = yearMonth.atEndOfMonth().getDayOfMonth();

        List<Integer> dailyVisitors = IntStream.rangeClosed(1, lastDayOfMonth)
                .mapToObj(day -> {
                    // 요청된 달과 일로 LocalDate 객체를 생성합니다.
                    LocalDate date = yearMonth.atDay(day);
                    return dailyVisitorService.getVisitorCount(date);
                })
                .toList();

        return dailyVisitors;
    }

    @GetMapping("/api/users")
    @ResponseBody
    public List<Integer> getMonthlyUserRegistrations(@RequestParam int year) {
        // 모든 사용자를 가져온다.
        List<UserDTO> allUsers = userService.getAllUsers();

        // 주어진 연도에 맞는 사용자만 필터링한다.
        List<UserDTO> usersInYear = allUsers.stream()
                .filter(user -> user.getCreationDate().getYear() == year) // 사용자의 가입 연도가 선택한 연도와 같은지 필터링
                .toList();

        // 연도에 해당하는 사용자들을 가입 월 기준으로 그룹화하여 월별 가입자 수를 계산한다.
        Map<Integer, Long> monthlyCounts = usersInYear.stream()
                .collect(Collectors.groupingBy(
                        user -> user.getCreationDate().getMonthValue(), // 사용자의 가입 월을 기준으로 그룹화
                        Collectors.counting() // 각 그룹의 수를 계산
                ));

        // 1월부터 12월까지 순서대로 가입자 수 리스트를 만든다.
        return IntStream.rangeClosed(1, 12) // 1부터 12까지의 정수 스트림을 생성
                .mapToObj(month -> monthlyCounts.getOrDefault(month, 0L).intValue()) // 각 월에 대한 가입자 수를 가져오고, 없으면 0으로 대체
                .collect(Collectors.toList()); // 결과를 리스트로 수집하여 반환
    }




    @PostMapping("/shelter/approve/{id}")
    public String approveShelter(@PathVariable("id") String id) {
        UserDTO userDTO = userService.getUserById(id);
        userService.updateUserType(id,userDTO);
        shelterService.approveShelterApplication(id);
        return "redirect:/superadmin/admin/dashboard";
    }

    @PostMapping("/shelter/reject/{id}")
    public String rejectShelter(@PathVariable("id") String id) {
        shelterService.rejectShelterApplication(id);
        return "redirect:/superadmin/admin/dashboard";
    }

    @PostMapping("/shelter/delete/{id}")
    public String deleteShelter(@PathVariable("id") String id) {
        UserDTO userDTO = userService.getUserById(id);
        userService.updateUserType(id,userDTO);
        shelterService.deleteShelter(id);
        return "redirect:/superadmin/admin/dashboard";
    }

    @PostMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable("id") String id) {
         userService.deleteUser(id);
        return "redirect:/superadmin/admin/dashboard";
    }


}
