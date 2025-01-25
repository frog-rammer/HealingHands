package org.zerock.mmh.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.zerock.mmh.dto.UserMemberDTO;
import org.zerock.mmh.service.UserMemberService;

@Controller
@RequestMapping("/userMemberController")
@Log4j2
@RequiredArgsConstructor
public class userMemberController {
    private final UserMemberService userMemberService;

    @GetMapping("/")
    public String userMember() {
        return "userMember";
    }
    @GetMapping("/userMember/new")
    public String createMemberForm(){
        return "/userMember/register";
    }
    @PostMapping
    public String createMember(UserMemberDTO userMemberDTO) {
//        String userMemNo = UserMemberService.join(userMemberDTO);
        return "userMember";
    }
}
