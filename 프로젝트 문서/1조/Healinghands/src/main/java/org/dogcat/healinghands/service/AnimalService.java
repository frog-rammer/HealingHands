
package org.dogcat.healinghands.service;

import org.dogcat.healinghands.dto.AnimalDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AnimalService {
    AnimalDTO createAnimal(AnimalDTO animalDTO, MultipartFile[] images);
    AnimalDTO getAnimalById(Long id);
    List<AnimalDTO> getAllAnimals();
    void updateAnimal(Long id, AnimalDTO animalDTO, MultipartFile[] images);
    void deleteAnimal(Long id);
    Page<AnimalDTO> getAnimals(Pageable pageable);

    List<AnimalDTO> getAnimalsByShelterId(String shelterId);
    List<AnimalDTO> getAnimalsByIds(List<Long> animalIds);
    Page<AnimalDTO> getFilteredAnimals(List<String> species, String breed, String shelterName, Pageable pageable);
    List<AnimalDTO> getRecentAnimals(int count);
}



