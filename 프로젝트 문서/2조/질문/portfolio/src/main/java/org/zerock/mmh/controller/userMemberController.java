package org.zerock.mmh.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.zerock.mmh.dto.UserMemberDTO;
import org.zerock.mmh.entity.UserMember;
import org.zerock.mmh.service.UserMemberService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/userMemberController")
@Log4j2
@RequiredArgsConstructor
public class userMemberController {
    private final UserMemberService userMemberService;

//    @GetMapping("/userMember")
//    public String userMember() {
//        return "loginSuccess";
//    }

    @GetMapping("/register")
    public String createMemberForm() {

        return "/userMember/register";
    }

    @GetMapping("/loginForm")
    public String loginForm() {

        return "/userMember/loginForm";
    }

    @GetMapping("/joinSuccess")
    public String joinSuccess() {

        return "/userMember/joinSuccess";
    }

    @PostMapping("/join")
    public String join(@ModelAttribute UserMemberDTO userMemberDTO) {
        try {
            // 회원 가입 서비스 호출
            String userMemNo = userMemberService.join(userMemberDTO);
            log.info("User registered successfully: {}", userMemNo);
            return "redirect:/userMemberController/joinSuccess";
        } catch (IllegalArgumentException e) {
            log.error("Error occurred while registering user: {}", e.getMessage());
            return "redirect:/userMemberController/register?error=true"; // 에러 파라미터 추가
        }
    }
    @PostMapping("/login")
    public String login(@ModelAttribute UserMemberDTO.LoginDTO loginDTO) {
        try {
            // 비밀번호가 null이면 에러 처리
            if (loginDTO.getUser_mem_pw() == null || loginDTO.getUser_mem_pw().isEmpty()) {
                log.error("Password cannot be null or empty");
                return "redirect:/userMemberController/loginForm?error=true";
            }

            UserMember userMember = userMemberService.login(loginDTO);
            log.info("User logged in successfully: {}", userMember.getUserMemId());

            // 권한 설정 (예: ROLE_USER)
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            // 필요한 권한 추가

            // 세션에 사용자 정보 저장
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    userMember.getUserMemId(),
                    loginDTO.getUser_mem_pw(),  //
                    authorities
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            return "redirect:/userMember/loginSuccess";
        } catch (IllegalArgumentException e) {
            log.error("Error occurred during login: {}", e.getMessage());
            return "redirect:/userMemberController/loginForm?error=true";
        }
    }

    @GetMapping("/loginSuccess")
    public String loginSuccess() {
        return "userMember/loginSuccess";
    }



}
