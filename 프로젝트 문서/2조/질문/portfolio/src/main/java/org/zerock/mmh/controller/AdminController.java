package org.zerock.mmh.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.zerock.mmh.dto.ManufacturerMemberDTO;
import org.zerock.mmh.entity.ManufacturerMember;
import org.zerock.mmh.service.ManufacturerMemberService;

import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final ManufacturerMemberService manufacturerMemberService;

    @GetMapping("/manufacturerList")
    public String manufacturerList(Model model) {
        List<ManufacturerMember> manufacturers = manufacturerMemberService.findAll();
        model.addAttribute("manufacturers", manufacturers);
        return "manufacturerMember/manufacturerList";
    }

    @PostMapping("/approve/{manuMemId}")
    public String approveManufacturer(@PathVariable String manuMemId) {
        manufacturerMemberService.approveManufacturerMember(manuMemId);
        return "redirect:/admin/manufacturerList";
    }

    @GetMapping("/superAdminRegister")
    public String superAdminRegisterForm() {
        return "SuperAdmin/superAdminRegister";
    }

    @PostMapping("/superAdminJoin")
    public String superAdminJoin(@ModelAttribute ManufacturerMemberDTO manufacturerMemberDTO) {
        manufacturerMemberDTO.setSuperAdmin(true);
        try {
            String manuMemNo = manufacturerMemberService.join(manufacturerMemberDTO);
            return "redirect:/admin/success";
        } catch (IllegalArgumentException e) {
            return "redirect:/admin/superAdminRegister?error=true";
        }
    }

    @GetMapping("/success")
    public String success() {
        return "SuperAdmin/success";
    }
}
