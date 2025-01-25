package org.dogcat.healinghands.controller;

import jakarta.servlet.http.HttpSession;
import org.dogcat.healinghands.dto.AnimalDTO;
import org.dogcat.healinghands.dto.UserDTO;
import org.dogcat.healinghands.service.AnimalService;
import org.dogcat.healinghands.service.ShelterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;
import org.dogcat.healinghands.dto.ShelterDTO;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/animals")
public class AnimalController {

    @Autowired
    private AnimalService animalService;

    @Autowired
    private ShelterService shelterService;

    @GetMapping("/register1")
    public String animalRegister(Model model) {
        model.addAttribute("animalDTO",  new AnimalDTO());
        return "animalregisterview"; // animalregisterview.html
    }

    @PostMapping("/register")
    public RedirectView createAnimal(
            @ModelAttribute AnimalDTO animalDTO,
            @RequestParam("shelterDate") String shelterDate,
            @RequestParam("images") MultipartFile[] images,
            HttpSession session) {

        UserDTO user = (UserDTO) session.getAttribute("user");

        // shelterDate를 LocalDate로 변환하여 animalDTO에 설정
        LocalDate date = LocalDate.parse(shelterDate);
        animalDTO.setShelterDate(date);
        animalDTO.setShelterId(user.getUserId());

        // 동물 정보 생성 서비스 호출
        animalService.createAnimal(animalDTO, images);
        return new RedirectView("/animals/list");
    }


    @GetMapping("/modify/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        AnimalDTO animalDTO = animalService.getAnimalById(id);
        model.addAttribute("animalDTO", animalDTO);
        return "animalmodifyview"; // 수정할 뷰 이름
    }


    @PostMapping("/modify/{id}")
    public RedirectView modifyAnimal(
            @PathVariable Long id,
            @ModelAttribute AnimalDTO animalDTO,
            @RequestParam("shelterDate") String shelterDate,
            @RequestParam(value = "images", required = false) MultipartFile[] images) {

        // 동물 수정 메서드 호출
        animalService.updateAnimal(id, animalDTO, images);
        return new RedirectView("/animals/list");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteAnimal(@PathVariable Long id) {
        animalService.deleteAnimal(id);
        return ResponseEntity.noContent().build(); // 204 No Content 반환
    }


    @GetMapping("/detail/{id}")
    public String getAnimalDetail(@PathVariable Long id, Model model) {
        AnimalDTO animalDTO = animalService.getAnimalById(id);
        model.addAttribute("animalDTO", animalDTO);
        return "animaldetail"; // 동물 상세 정보를 보여줄 뷰 이름
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnimalDTO> getAnimal(@PathVariable Long id) {
        AnimalDTO animal = animalService.getAnimalById(id);
        return animal != null ? ResponseEntity.ok(animal) : ResponseEntity.notFound().build();
    }


    @GetMapping("/list")
    public String showAnimalList(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "12") int size,
                                 @RequestParam(required = false) List<String> species,
                                 @RequestParam(required = false) String breed,
                                 @RequestParam(required = false) String shelterName,
                                 Model model) {

        Pageable pageable = PageRequest.of(page, size);

        // 빈 문자열을 null로 변환
        breed = (breed != null && !breed.trim().isEmpty()) ? breed : null;
        shelterName = (shelterName != null && !shelterName.trim().isEmpty()) ? shelterName : null;
        if (species != null && species.isEmpty()) {
            species = null;
        }

        Page<AnimalDTO> animalsPage = animalService.getFilteredAnimals(species, breed, shelterName, pageable);

        // Shelter 리스트를 불러와 필터링 옵션에 추가
        List<ShelterDTO> shelters = shelterService.getAllShelters();

        // 모델에 데이터 추가
        model.addAttribute("animalsPage", animalsPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", animalsPage.getTotalPages());
        model.addAttribute("shelters", shelters);

        return "new_family";  // HTML 파일 이름
    }

}
