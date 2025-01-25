package org.dogcat.healinghands.service;

import org.dogcat.healinghands.dto.AdoptionDTO;
import org.dogcat.healinghands.entity.Adoption;
import org.dogcat.healinghands.repository.AdoptionRepository;
import org.dogcat.healinghands.repository.ShelterRepository;
import org.dogcat.healinghands.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdoptionServiceImpl implements AdoptionService {

    @Autowired
    private AdoptionRepository adoptionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ShelterRepository shelterRepository;

    @Override
    public AdoptionDTO saveAdoption(AdoptionDTO adoptionDTO) {
        Adoption adoption = convertToEntity(adoptionDTO);
        Adoption savedAdoption = adoptionRepository.save(adoption);
        return convertToDTO(savedAdoption);
    }

    @Override
    public Page<AdoptionDTO> getAdoptionByShelterId(String shelterId, Pageable pageable) {
        Page<Adoption> adoptionPage = adoptionRepository.findByShelterId(shelterId, pageable);
        return adoptionPage.map(this::convertToDTO);
    }

    @Override
    public AdoptionDTO createAdoption(AdoptionDTO adoptionDTO) {
        Adoption savedAdoption = adoptionRepository.save(convertToEntity(adoptionDTO));
        return convertToDTO(savedAdoption);
    }

    @Override
    public AdoptionDTO getAdoptionById(Long id) {
        return adoptionRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    @Override
    public List<AdoptionDTO> getAllAdoptions() {
        return adoptionRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AdoptionDTO updateAdoption(Long id, AdoptionDTO adoptionDTO) {
        return adoptionRepository.findById(id)
                .map(existingAdoption -> {
                    updateEntityWithDTO(existingAdoption, adoptionDTO);
                    return convertToDTO(adoptionRepository.save(existingAdoption));
                }).orElse(null);
    }

    @Override
    public void deleteAdoption(Long id) {
        adoptionRepository.deleteById(id);
    }

    @Override
    public List<AdoptionDTO> findAdoptionsByName(String name) {
        return adoptionRepository.findAllByName(name).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // AdoptionService 클래스 내

    private void updateEntityWithDTO(Adoption entity, AdoptionDTO dto) {
        entity.setName(dto.getName());
        entity.setContact(dto.getContact());
        entity.setEmail(dto.getEmail());
        entity.setGender(dto.getGender());
        entity.setAge(dto.getAge());
        entity.setAddress(dto.getAddress());
        entity.setConsent(dto.isConsent());
        entity.setAdoptionQA(dto.getAdoptionQA());
        entity.setShelterId(dto.getShelterId()); // ShelterId 추가
        entity.setUserId(dto.getUserId());       // UserId 추가
        entity.setAnimalId(dto.getAnimalId());   // AnimalId 추가
    }

    private Adoption convertToEntity(AdoptionDTO dto) {
        return Adoption.builder()
                .userId(dto.getUserId())
                .shelterId(dto.getShelterId())
                .animalId(dto.getAnimalId())     // AnimalId 추가
                .name(dto.getName())
                .contact(dto.getContact())
                .email(dto.getEmail())
                .gender(dto.getGender())
                .age(dto.getAge())
                .address(dto.getAddress())
                .consent(dto.isConsent())
                .adoptionQA(dto.getAdoptionQA())
                .build();
    }

    private AdoptionDTO convertToDTO(Adoption entity) {
        return AdoptionDTO.builder()
                .userId(entity.getUserId())
                .shelterId(entity.getShelterId())
                .animalId(entity.getAnimalId())  // AnimalId 추가
                .name(entity.getName())
                .contact(entity.getContact())
                .email(entity.getEmail())
                .gender(entity.getGender())
                .age(entity.getAge())
                .address(entity.getAddress())
                .consent(entity.isConsent())
                .adoptionQA(entity.getAdoptionQA())
                .build();
    }

}
