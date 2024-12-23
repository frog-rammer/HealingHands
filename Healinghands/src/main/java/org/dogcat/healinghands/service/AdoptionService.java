package org.dogcat.healinghands.service;

import org.dogcat.healinghands.dto.AdoptionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AdoptionService {
    AdoptionDTO createAdoption(AdoptionDTO adoptionDTO);
    AdoptionDTO getAdoptionById(Long id);
    List<AdoptionDTO> getAllAdoptions();
    AdoptionDTO updateAdoption(Long id, AdoptionDTO adoptionDTO);
    void deleteAdoption(Long id);
    List<AdoptionDTO> findAdoptionsByName(String name); // 추가된 메서드
    AdoptionDTO saveAdoption(AdoptionDTO adoptionDTO);
    public Page<AdoptionDTO> getAdoptionByShelterId(String shelterId, Pageable pageable);
}
