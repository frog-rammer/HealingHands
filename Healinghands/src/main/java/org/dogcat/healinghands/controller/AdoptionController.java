package org.dogcat.healinghands.controller;

import jakarta.servlet.http.HttpSession;
import org.dogcat.healinghands.dto.AdoptionDTO;
import org.dogcat.healinghands.dto.AnimalDTO;
import org.dogcat.healinghands.dto.UserDTO;
import org.dogcat.healinghands.service.AdoptionService;
import org.dogcat.healinghands.service.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/adoptions")
public class AdoptionController {

    @Autowired
    private AdoptionService adoptionService;
    @Autowired
    private AnimalService animalService;

    @GetMapping("/guideApplication/{id}")
    public String showGuideApplication(@PathVariable Long id, Model model, HttpSession session) {
        if(session.getAttribute("user") == null) {
            return "redirect:/users/login";
        }
        AnimalDTO animalDTO = animalService.getAnimalById(id);
        model.addAttribute("adoptionDTO", new AdoptionDTO());
        UserDTO user = (UserDTO)session.getAttribute("user");
        model.addAttribute("userDTO", user);
        model.addAttribute("animalDTO", animalDTO);
        return "guideApplication";
    }


    @PostMapping("/application")
    public String submitApplication(@ModelAttribute("adoptionDTO") AdoptionDTO adoptionDTO,  @RequestParam("animalId") Long animalId,
                                    @RequestParam("shelterId") String shelterId, HttpSession session) {
        // AdoptionService의 saveAdoption 메서드를 호출하여 데이터 저장
        UserDTO user = (UserDTO)session.getAttribute("user");
        adoptionDTO.setAddress(user.getUserAddress());
        adoptionDTO.setName(user.getUsername());
        adoptionDTO.setUserId(user.getUserId());
        adoptionDTO.setEmail(user.getEmail());
        adoptionDTO.setContact(user.getPhone());
        adoptionDTO.setShelterId(shelterId);
        adoptionDTO.setAnimalId(animalId);

        adoptionService.saveAdoption(adoptionDTO);

        // 성공 후 리스트 페이지로 리다이렉트
        return "redirect:/animals/list";
    }
    @GetMapping("/{id}")
    public ResponseEntity<AdoptionDTO> getAdoption(@PathVariable Long id) {
        AdoptionDTO adoption = adoptionService.getAdoptionById(id);
        if (adoption != null) {
            return new ResponseEntity<>(adoption, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/adoption/{id}")
    public AdoptionDTO getAdoptionById(@PathVariable Long id) {
        return adoptionService.getAdoptionById(id);
    }


    @GetMapping
    public ResponseEntity<List<AdoptionDTO>> getAllAdoptions() {
        List<AdoptionDTO> adoptions = adoptionService.getAllAdoptions();
        return new ResponseEntity<>(adoptions, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdoptionDTO> updateAdoption(@PathVariable Long id, @RequestBody AdoptionDTO adoptionDTO) {
        AdoptionDTO updatedAdoption = adoptionService.updateAdoption(id, adoptionDTO);
        if (updatedAdoption != null) {
            return new ResponseEntity<>(updatedAdoption, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdoption(@PathVariable Long id) {
        if (adoptionService.getAdoptionById(id) != null) {
            adoptionService.deleteAdoption(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
