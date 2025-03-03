package org.dogcat.healinghands.controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.dogcat.healinghands.dto.UserDTO;
import org.dogcat.healinghands.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/profile/update")
    public String updateProfile(@ModelAttribute UserDTO userDTO) {
        String userId = userDTO.getUserId();
        userService.updateUser(userId, userDTO);
        return "redirect:/myPage/list";
    }

    @PostMapping("/mypage/confirmUpdate")
    public String confirmUpdate(@RequestParam String password, HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        UserDTO loggedInUser = (UserDTO) session.getAttribute("user");

        if (userService.authenticate(loggedInUser.getUserId(), password, loggedInUser.getUserType()) != null) {
            return "redirect:/profile";
        } else {
            model.addAttribute("error", "비밀번호가 일치하지 않습니다.");
            return "mypageview";
        }
    }
    @GetMapping("/profile")
    public String showProfile(Principal principal, Model model) {
        if (principal == null) {
            return "redirect:/users/login";
        }
        String userId = principal.getName();
        UserDTO userDTO = userService.getUserById(userId);
        model.addAttribute("user", userDTO);
        return "profile";
    }
    @PostMapping("/mypage/verify-password")
    public String verifyPassword(@RequestParam String password) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) auth.getPrincipal();

        if (passwordEncoder.matches(password, userDetails.getPassword())) {
            return "mypageview";
        } else {
            return "error";
        }
    }


    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        return "register";
    }

    @PostMapping("/register")
    public String createUser(@ModelAttribute UserDTO userDTO, Model model) {
        UserDTO createdUser = userService.createUser(userDTO);
        model.addAttribute("user", createdUser);
        return "registration_success";
    }

    @GetMapping("/checkUserId")
    @ResponseBody // JSON 응답을 반환하도록 설정
    public boolean checkUserId(@RequestParam String userId) {
        return !userService.isUserIdExists(userId);
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute UserDTO userDTO, HttpServletRequest request, HttpServletResponse response, Model model) {
        try {
            // 로그인 폼에서 전달된 userId와 password로 인증
            if (userDTO.getUserId() == null || userDTO.getUserId().isEmpty()) {
                model.addAttribute("error", "User ID is required");
                return "login"; // userId가 없으면 오류 반환
            }

            UserDTO authenticatedUser = userService.authenticate(userDTO.getUserId(), userDTO.getPassword(), userDTO.getUserType());

            if (authenticatedUser != null) {
                HttpSession session = request.getSession();
                session.setMaxInactiveInterval(60 * 60 * 12); // 세션 유효시간을 12시간으로 설정
                session.setAttribute("user", authenticatedUser);
                model.addAttribute("user", authenticatedUser);
                System.out.println("로그인 성공!!!!");
                return "redirect:/"; // 로그인 성공 시 루트 경로로 리다이렉트
            } else {
                model.addAttribute("error", "아이디 또는 비밀번호가 잘못되었습니다.");
                return "login";
            }
        } catch (IllegalArgumentException e) {
            // 예외 발생 시 오류 메시지 추가
            model.addAttribute("error", e.getMessage());
            return "login";
        }
    }


    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); // 세션 무효화
        }
        return "redirect:/";
    }

    @PostMapping("/signup")
    public String signup(@ModelAttribute UserDTO userDTO) {
        userService.createUser(userDTO); // 사용자 생성 로직
        return "redirect:/users/login"; // 성공 페이지로 리다이렉트
    }

    @GetMapping("/{id}")
    public String getUser(@PathVariable String id, Model model) {
        UserDTO userDTO = userService.getUserById(id);
        model.addAttribute("user", userDTO);
        return "user_profile";
    }

    @GetMapping
    public String getAllUsers(Model model) {
        List<UserDTO> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "user_list";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable String id, Model model) {
        UserDTO userDTO = userService.getUserById(id);
        model.addAttribute("userDTO", userDTO);
        return "edit_user";
    }

    @PostMapping("/edit/{id}")
    public String updateUser(@PathVariable String id, @ModelAttribute UserDTO userDTO, Model model) {
        UserDTO updatedUser = userService.updateUser(id, userDTO);
        model.addAttribute("user", updatedUser);
        return "edit_success";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable String id, Model model) {
        userService.deleteUser(id);
        return "redirect:/users";
    }

}
