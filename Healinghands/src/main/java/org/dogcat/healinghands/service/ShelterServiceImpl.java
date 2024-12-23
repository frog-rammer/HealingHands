package org.dogcat.healinghands.service;

import org.dogcat.healinghands.dto.ShelterDTO;
import org.dogcat.healinghands.entity.Shelter;
import org.dogcat.healinghands.entity.WaitingForShelterApp;
import org.dogcat.healinghands.repository.ShelterRepository;
import org.dogcat.healinghands.repository.WaitingForShelterAppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShelterServiceImpl implements ShelterService {

    @Autowired
    private ShelterRepository shelterRepository;


    @Autowired
    private WaitingForShelterAppRepository waitingForShelterAppRepository; // 추가

    private final String UPLOAD_DIR = "src/main/resources/static/images/"; // 파일 저장 경로

    /*
     * 보호소 신청
     */
    @Override
    public Page<ShelterDTO> getAllWaitingSheltersPageable(Pageable pageable) {
        Page<WaitingForShelterApp> waitingSheltersPage = waitingForShelterAppRepository.findAll(pageable);
        return waitingSheltersPage.map(this::waitShelterConvertToDTO);
    }
    @Override
    public Page<ShelterDTO> searchWaitingShelters(String query, Pageable pageable) {
        Page<WaitingForShelterApp> waitingSheltersPage = waitingForShelterAppRepository.findByShelterNameContainingIgnoreCase(query, pageable);
        return waitingSheltersPage.map(this::waitShelterConvertToDTO);
    }

    @Override
    public boolean isUserIdExists(String userId) {
        return false;
    }

    @Override
    public void rejectShelterApplication(String shelterId) {
        // 보호소 신청 엔티티 삭제
        WaitingForShelterApp waitingShelter = waitingForShelterAppRepository.findById(shelterId).orElseThrow(() -> new IllegalArgumentException("Invalid shelter ID: " + shelterId));
        waitingForShelterAppRepository.delete(waitingShelter);
    }
    @Override
    public void deleteShelter(String shelterId) {
        // 보호소 엔티티 삭제
        Shelter shelter = shelterRepository.findByShelterId(shelterId);
        shelterRepository.delete(shelter);
    }

    @Override
    public boolean isshelterNameExists(String shelterName) {
        return shelterRepository.existsByshelterName(shelterName);
    }

    @Override
    public void approveShelterApplication(String shelterId) {
        // 보호소 신청 엔티티를 찾고 보호소 엔티티로 변환 후 저장
        WaitingForShelterApp waitingShelter = waitingForShelterAppRepository.findById(shelterId).orElseThrow(() -> new IllegalArgumentException("Invalid shelter ID: " + shelterId));
        Shelter shelter = shelterConvertToEntity(waitShelterConvertToDTO(waitingShelter));
        shelterRepository.save(shelter);
        // 보호소 신청 엔티티 삭제
        waitingForShelterAppRepository.delete(waitingShelter);
    }

    @Override
    public List<ShelterDTO> getAllShelters() {
        return shelterRepository.findAll().stream()
                .map(this::shelterEntityToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ShelterDTO getShelterById(String id) {
        return shelterEntityToDTO(shelterRepository.findByShelterId(id));
    }
    @Override
    public Page<ShelterDTO> getAllSheltersPageable(Pageable pageable) {
        Page<Shelter> sheltersPage = shelterRepository.findAll(pageable);
        return sheltersPage.map(this::shelterEntityToDTO);
    }
    @Override
    public Page<ShelterDTO> searchShelters(String query, Pageable pageable) {
        Page<Shelter> shelterPage = shelterRepository.findByShelterNameContainingIgnoreCase(query, pageable);
        return shelterPage.map(this::shelterEntityToDTO);
    }


    // 파일 저장 메서드
    private String saveFile(MultipartFile file) {
        if (file != null && !file.isEmpty()) {
            try {
                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                File destinationFile = new File(UPLOAD_DIR + fileName);
                file.transferTo(destinationFile); // 파일 저장
                return fileName; // 저장된 파일 이름 반환
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null; // 파일 저장 실패
    }
    // 보호소 신청 관련 변환 메서드
    private WaitingForShelterApp waitShelterConvertToEntity(ShelterDTO shelterDTO) {
        return WaitingForShelterApp.builder()
                .shelterId(shelterDTO.getShelterId())
                .shelterName(shelterDTO.getShelterName())
                .address(shelterDTO.getAddress())
                .contactInfo(shelterDTO.getContactInfo())
                .operatingHours(shelterDTO.getOperatingHours())
                .animalCount(shelterDTO.getAnimalCount())
                .introduction(shelterDTO.getServices()) // 서비스 내용을 소개로 사용
                .shelterPhotoPath(shelterDTO.getShelterPhotoPath()) // 경로 추가
                .build();
    }
    private ShelterDTO waitShelterConvertToDTO(WaitingForShelterApp shelter) {
        return ShelterDTO.builder()
                .shelterId(shelter.getShelterId())
                .shelterName(shelter.getShelterName())
                .address(shelter.getAddress())
                .contactInfo(shelter.getContactInfo())
                .operatingHours(shelter.getOperatingHours())
                .animalCount(shelter.getAnimalCount())
                .services(shelter.getIntroduction()) // 소개를 서비스로 사용
                .shelterPhotoPath(shelter.getShelterPhotoPath())
                .build();
    }

    // 보호소 관련 변환 메서드
    private ShelterDTO shelterEntityToDTO(Shelter shelter) {
        return ShelterDTO.builder()
                .shelterId(shelter.getShelterId())
                .shelterName(shelter.getShelterName())
                .address(shelter.getAddress())
                .contactInfo(shelter.getContactInfo())
                .operatingHours(shelter.getOperatingHours())
                .animalCount(shelter.getAnimalCount())
                .services(shelter.getServices()) // 서비스 정보를 그대로 사용
                .shelterPhotoPath(shelter.getShelterPhotoPath())
                .build();
    }

    private Shelter shelterConvertToEntity(ShelterDTO shelterDTO) {
        return Shelter.builder()
                .shelterId(shelterDTO.getShelterId())
                .shelterName(shelterDTO.getShelterName())
                .address(shelterDTO.getAddress())
                .contactInfo(shelterDTO.getContactInfo())
                .operatingHours(shelterDTO.getOperatingHours())
                .animalCount(shelterDTO.getAnimalCount())
                .services(shelterDTO.getServices()) // 서비스 정보를 그대로 사용
                .shelterPhotoPath(shelterDTO.getShelterPhotoPath())
                .build();
    }
    @Override
    public ShelterDTO insertWaitShelter(ShelterDTO shelterDTO, MultipartFile shelterPhoto) {
        try {
            // 파일 저장 경로 설정
            String imageUrls = processImage(shelterPhoto, shelterDTO.getShelterPhotoPath());

            shelterDTO.setShelterPhotoPath(imageUrls); // DTO 에 파일 경로 설정

            // ShelterDTO를 WaitingForShelterApp 엔티티로 변환
            WaitingForShelterApp shelter = waitShelterConvertToEntity(shelterDTO);

            // 보호소 저장
            WaitingForShelterApp savedShelter = waitingForShelterAppRepository.save(shelter);

            // 저장된 엔티티를 DTO로 변환하여 반환
            return waitShelterConvertToDTO(savedShelter);
        } catch (IOException e) {
            System.err.println("이미지 처리 중 오류 발생: " + e.getMessage());
            throw new RuntimeException("이미지 저장에 실패했습니다.", e); // 사용자 정의 예외를 던짐
        }
    }

    private String processImage(MultipartFile image, String existingImageUrls) throws IOException {
        StringBuilder imageUrls = new StringBuilder();

        // 기존 이미지 URL이 있을 경우 추가
        if (existingImageUrls != null && !existingImageUrls.isEmpty()) {
            imageUrls.append(existingImageUrls).append(",");
        }

        String originalFileName = StringUtils.cleanPath(image.getOriginalFilename());
        String fileExtension = ".jpg"; // 확장자를 jpg로 고정

        // 새 파일명 설정
        String fileName = originalFileName.substring(0, originalFileName.lastIndexOf('.')) + fileExtension;
        Path path = Paths.get(UPLOAD_DIR, fileName);

        // 중복 파일명 처리
        int count = 1;
        while (Files.exists(path)) {
            fileName = originalFileName.substring(0, originalFileName.lastIndexOf('.')) + "(" + count + ")" + fileExtension;
            path = Paths.get(UPLOAD_DIR, fileName);
            count++;
        }

        // 파일 저장
        try (InputStream inputStream = image.getInputStream()) {
            Files.copy(inputStream, path);
            System.out.println("이미지 저장됨: " + path.toString());
            imageUrls.append("/images/").append(path.getFileName()).append(","); // URL 생성
        } catch (IOException e) {
            System.err.println("파일 저장 오류: " + e.getMessage());
            throw e;
        }

        // 마지막 구분자 제거
        if (imageUrls.length() > 0) {
            imageUrls.setLength(imageUrls.length() - 1); // 마지막 콤마 제거
        }

        return imageUrls.toString();
    }
}
