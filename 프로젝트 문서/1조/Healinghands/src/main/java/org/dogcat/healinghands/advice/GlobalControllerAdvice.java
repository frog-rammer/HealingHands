package org.dogcat.healinghands.advice;

import jakarta.servlet.http.HttpSession;
import org.dogcat.healinghands.dto.UserDTO;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ModelAttribute
    public void addUserToModel(HttpSession session, Model model) {
        UserDTO user = (UserDTO) session.getAttribute("user");
        model.addAttribute("user", user);
    }
}