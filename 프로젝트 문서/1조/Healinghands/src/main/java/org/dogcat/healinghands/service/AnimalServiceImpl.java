package org.dogcat.healinghands.service;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.dogcat.healinghands.dto.AnimalDTO;
import org.dogcat.healinghands.entity.Animal;
import org.dogcat.healinghands.entity.Shelter;
import org.dogcat.healinghands.repository.AnimalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnimalServiceImpl implements AnimalService {

    private final AnimalRepository animalRepository;
    private final String uploadDir = "src/main/resources/static/images/";

    @Autowired
    public AnimalServiceImpl(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    @Override
    public AnimalDTO createAnimal(AnimalDTO animalDTO, MultipartFile[] images) {
        try {
            validateAnimalDTO(animalDTO);

            Animal animal = mapToEntity(animalDTO);
            if (animal.getShelterDate() == null) {
                animal.setShelterDate(LocalDate.now());
            }

            // 이미지 처리
            String imageUrls = processImages(images, null); // 첫 생성이므로 기존 URL 없음
            animal.setImageUrl(imageUrls);

            Animal savedAnimal = animalRepository.save(animal);
            return mapToDTO(savedAnimal);
        } catch (DataIntegrityViolationException e) {
            System.err.println("데이터 무결성 오류 발생: " + e.getMessage());
            throw new RuntimeException("Animal 데이터를 저장하는 중 오류가 발생했습니다. 필드를 확인하세요.", e);
        } catch (Exception e) {
            System.err.println("Animal 데이터 저장 중 오류: " + e.getMessage());
            throw new RuntimeException("Animal 데이터를 저장하는 중 오류가 발생했습니다.", e);
        }
    }
    private void validateAnimalDTO(AnimalDTO animalDTO) {
        if (animalDTO.getName() == null || animalDTO.getName().isEmpty()) {
            throw new IllegalArgumentException("동물 이름이 필요합니다.");
        }
        if (animalDTO.getSpecies() == null || animalDTO.getSpecies().isEmpty()) {
            throw new IllegalArgumentException("종이 필요합니다.");
        }
        if (animalDTO.getBreed() == null || animalDTO.getBreed().isEmpty()) {
            throw new IllegalArgumentException("품종이 필요합니다.");
        }
        // 추가 유효성 검사...
    }

    @Override
    public Page<AnimalDTO> getFilteredAnimals(List<String> species, String breed, String shelterName, Pageable pageable) {
        Specification<Animal> spec = Specification.where(null);

        if (species != null && !species.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) -> root.get("species").in(species));
        }

        if (breed != null && !breed.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("breed"), "%" + breed + "%"));
        }

        if (shelterName != null && !shelterName.isEmpty()) {
            spec = spec.and((root, query, criteriaBuilder) -> {
                Join<Animal, Shelter> shelterJoin = root.join("shelter", JoinType.LEFT);
                return criteriaBuilder.like(shelterJoin.get("shelterName"), "%" + shelterName + "%");
            });
        }

        Page<Animal> animalsPage = animalRepository.findAll(spec, pageable);
        return animalsPage.map(this::mapToDTO);
    }


    @Override
    public Page<AnimalDTO> getAnimals(Pageable pageable) {
        Page<Animal> animalsPage = animalRepository.findAll(pageable);
        return animalsPage.map(this::mapToDTO);
    }

    @Override
    public List<AnimalDTO> getAllAnimals() {
        List<Animal> animals = animalRepository.findAll();
        return animals.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public AnimalDTO getAnimalById(Long id) {
        Animal animal = animalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 ID의 동물을 찾을 수 없습니다."));
        return mapToDTO(animal);
    }


    @Override
    public void updateAnimal(Long id, AnimalDTO animalDTO, MultipartFile[] images) {
        Animal animal = animalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("동물 없음"));

        // 동물 정보 업데이트
        animal.setName(animalDTO.getName());
        animal.setSpecies(animalDTO.getSpecies());
        animal.setBreed(animalDTO.getBreed());
        animal.setAge(animalDTO.getAge());
        animal.setWeight(animalDTO.getWeight());
        animal.setColor(animalDTO.getColor());
        animal.setGender(animalDTO.getGender());
        animal.setVaccinated(animalDTO.isVaccinated());
        animal.setAdoptable(animalDTO.isAdoptable());
        animal.setNeutered(animalDTO.isNeutered());
        animal.setDescription(animalDTO.getDescription());
        animal.setStatus(animalDTO.getStatus());
        animal.setShelterDate(animalDTO.getShelterDate());

        // 새로운 이미지가 있는 경우 처리
        if (images != null && images.length > 0) {
            try {
                // 기존 이미지 삭제
                deleteExistingImages(animal.getImageUrl());

                // 새 이미지 저장
                String imageUrls = processImages(images, animal.getImageUrl());
                animal.setImageUrl(imageUrls);
            } catch (IOException e) {
                System.err.println("이미지 처리 중 오류 발생: " + e.getMessage());
                throw new RuntimeException("이미지를 저장하는 중 오류가 발생했습니다.", e);
            }
        } else {
            // 이미지가 없는 경우 기존 URL을 유지
            animal.setImageUrl(animal.getImageUrl());
        }

        animalRepository.save(animal); // 업데이트된 동물 정보 저장
    }

    @Override
    public List<AnimalDTO> getAnimalsByShelterId(String shelterId) {
        return List.of();
    }
    @Override
    public List<AnimalDTO> getAnimalsByIds(List<Long> animalIds) {
        return animalRepository.findAllById(animalIds).stream()   //  findAllById 기본제공해줌.
                .map(this::mapToDTO)  // mapToDTO 메서드를 사용하여 엔티티를 DTO로 변환
                .collect(Collectors.toList());
    }
    @Override
    public void deleteAnimal(Long id) {
        Animal animal = animalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 ID의 동물을 찾을 수 없습니다."));

        if (animal.getImageUrl() != null) {
            deleteExistingImages(animal.getImageUrl());
        }
        animalRepository.delete(animal);
    }

    @Override
    public List<AnimalDTO> getRecentAnimals(int count) {
        List<Animal> recentAnimals = animalRepository.findTop5ByOrderByCreatedAtDesc();
        return recentAnimals.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    private AnimalDTO mapToDTO(Animal animal) {
        return AnimalDTO.builder()
                .animalId(animal.getAnimalId())
                .name(animal.getName())
                .species(animal.getSpecies())
                .breed(animal.getBreed())
                .age(animal.getAge())
                .weight(animal.getWeight())
                .color(animal.getColor())
                .gender(animal.getGender())
                .shelterDate(animal.getShelterDate())
                .status(animal.getStatus())
                .vaccinated(animal.isVaccinated())
                .adoptable(animal.isAdoptable())
                .neutered(animal.isNeutered())
                .description(animal.getDescription())
                .imageUrl(animal.getImageUrl())
                .build();
    }

    // 코드 1에서 사용된 animalEntityToDTO 메서드 추가
    public AnimalDTO animalEntityToDTO(Animal animal) {
        return mapToDTO(animal); // 일관성을 위해 mapToDTO를 호출
    }

    private Animal mapToEntity(AnimalDTO animalDTO) {
        return Animal.builder()
                .name(animalDTO.getName())
                .species(animalDTO.getSpecies())
                .breed(animalDTO.getBreed())
                .age(animalDTO.getAge())
                .gender(animalDTO.getGender())
                .color(animalDTO.getColor())
                .weight(animalDTO.getWeight())
                .shelterDate(animalDTO.getShelterDate())
                .vaccinated(animalDTO.isVaccinated())
                .adoptable(animalDTO.isAdoptable())
                .neutered(animalDTO.isNeutered())
                .description(animalDTO.getDescription())
                .shelterId(animalDTO.getShelterId())
                .status(animalDTO.getStatus())
                .build();
    }

    // 이미지 처리 메서드 수정: 기존 이미지 URL을 매개변수로 받도록 수정
    private String processImages(MultipartFile[] images, String existingImageUrls) throws IOException {
        StringBuilder imageUrls = new StringBuilder();

        for (MultipartFile image : images) {
            String originalFileName = StringUtils.cleanPath(image.getOriginalFilename());
            String fileExtension = ".jpg"; // 확장자를 jpg로 고정

            // 새 파일명 설정
            String fileName = originalFileName.substring(0, originalFileName.lastIndexOf('.')) + fileExtension;
            Path path = Paths.get(uploadDir, fileName);

            // 중복 파일명 처리
            int count = 1;
            while (Files.exists(path)) {
                fileName = originalFileName.substring(0, originalFileName.lastIndexOf('.')) + "(" + count + ")" + fileExtension;
                path = Paths.get(uploadDir, fileName);
                count++;
            }

            // 파일 저장
            try (InputStream inputStream = image.getInputStream()) {
                Files.copy(inputStream, path);
                System.out.println("이미지 저장됨: " + path.toString());
                imageUrls.append("/images/").append(path.getFileName()).append(","); // URL 생성
            } catch (IOException e) {
                System.err.println("파일 저장 오류: " + e.getMessage());
                throw e; // 예외를 다시 던짐
            }
        }

        // 마지막 구분자 제거
        if (imageUrls.length() > 0) {
            imageUrls.setLength(imageUrls.length() - 1); // 마지막 콤마 제거
        }

        return imageUrls.toString();
    }


    private void deleteExistingImages(String imageUrl) {
        if (imageUrl != null && !imageUrl.isEmpty()) {
            String[] urls = imageUrl.split(",");
            for (String url : urls) {
                Path path = Paths.get(uploadDir, url.substring(url.lastIndexOf('/') + 1));
                try {
                    Files.deleteIfExists(path);
                    System.out.println("삭제된 이미지: " + path.toString());
                } catch (IOException e) {
                    System.err.println("이미지 삭제 오류: " + e.getMessage());
                }
            }
        }
    }
}