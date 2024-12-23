package org.dogcat.healinghands.controller;

import jakarta.servlet.http.HttpSession;
import org.dogcat.healinghands.dto.UserDTO;
import org.dogcat.healinghands.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SidebarController{

    @RequestMapping("/import/header")
    public String getHeader() {
        return "import/header";
    }


    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // 세션 무효화
        return "redirect:/";
    }
}
