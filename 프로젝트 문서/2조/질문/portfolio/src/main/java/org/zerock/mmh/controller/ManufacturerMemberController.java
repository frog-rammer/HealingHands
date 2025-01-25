package org.zerock.mmh.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zerock.mmh.dto.ManufacturerMemberDTO;
import org.zerock.mmh.entity.ManufacturerMember;
import org.zerock.mmh.service.ManufacturerMemberService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/manufacturerMemberController")
@Log4j2
@RequiredArgsConstructor
public class ManufacturerMemberController {
    private final ManufacturerMemberService manufacturerMemberService;

    @GetMapping("/register")
    public String createMemberForm() {
        return "/manufacturerMember/register";
    }

    @GetMapping("/loginForm")
    public String loginForm() {
        return "/manufacturerMember/loginForm";
    }

    @GetMapping("/joinSuccess")
    public String joinSuccess() {
        return "/manufacturerMember/joinSuccess";
    }

    @PostMapping("/join")
    public String join(@ModelAttribute ManufacturerMemberDTO manufacturerMemberDTO) {
        try {
            String manuMemNo = manufacturerMemberService.join(manufacturerMemberDTO);
            log.info("User registered successfully: {}", manuMemNo);
            return "redirect:/manufacturerMemberController/joinSuccess";
        } catch (IllegalArgumentException e) {
            log.error("Error occurred while registering user: {}", e.getMessage());
            return "redirect:/manufacturerMemberController/register?error=true";
        } catch (Exception e) {
            log.error("Unexpected error occurred: {}", e.getMessage());
            return "redirect:/manufacturerMemberController/register?error=true";
        }
    }

    @PostMapping("/login")
    public String login(@ModelAttribute ManufacturerMemberDTO.LoginDTO loginDTO) {
        try {
            ManufacturerMember manufacturerMember = manufacturerMemberService.login(loginDTO);
            log.info("User logged in successfully: {}", manufacturerMember.getManuMemId());

            // Spring Security의 AuthenticationManager를 통해 인증을 처리
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_FACTURER"));

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    manufacturerMember.getManuMemId(),
                    null,
                    authorities
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            return "redirect:/manufacturerMember/loginSuccess";
        } catch (IllegalArgumentException e) {
            log.error("Error occurred during login: {}", e.getMessage());
            return "redirect:/manufacturerMemberController/loginForm?error=" + e.getMessage();
        } catch (Exception e) {
            log.error("Unexpected error occurred during login: {}", e.getMessage());
            return "redirect:/manufacturerMemberController/loginForm?error=" + e.getMessage();
        }
    }


    @GetMapping("/loginSuccess")
    public String loginSuccess() {
        return "/manufacturerMember/loginSuccess";
    }
}
