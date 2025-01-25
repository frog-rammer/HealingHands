package org.dogcat.healinghands.controller;

import org.dogcat.healinghands.dto.AnimalDTO;
import org.dogcat.healinghands.dto.UserDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/users/signup")
    public String showSignupForm(Model model) {
        model.addAttribute("userDTO", new UserDTO()); // DTO 객체 추가
        System.out.println("Signup form accessed"); // 로그 추가
        return "signup"; // signup.html로 이동
    }
    @GetMapping("/address")
    public String address() {
        return "address"; // address.html
    }
    @GetMapping("/profile")
    public String profile() {
        return "profile";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard"; // dashboard.html
    }


    @GetMapping("/animal/register")
    public String animalRegister(Model model) {
        model.addAttribute("animalDTO", new AnimalDTO());
        return "animalregisterview"; // animalregisterview.html
    }


    @GetMapping("/animal/modify")
    public String animalModify() {
        return "animalmodifyview"; // animalmodifyview.html
    }


    @GetMapping("/guide")
    public String guide() {
        return "guideApplication";
    }


    @GetMapping("/service/update")
    public String serviceUpdate() {
        return "serviceUpdateForm";
    }



    @GetMapping("/sitemap")
    public String sitemap() {
        return "sitemap"; // sitemap.html
    }

    @GetMapping("/superadmin")
    public String superAdmin() {
        return "superAdmin"; // superAdmin.html
    }

    @GetMapping("/volunteer")
    public String volunteer() {
        return "volunteer";
    }

    @GetMapping("/volunteer/board")
    public String volunteerBoard() {
        return "volunteerBoard"; // volunteerBoard.html
    }

    @GetMapping("/company_introduce")
    public String companyIntroduce() {
        return "company_introduce";
    }
}
