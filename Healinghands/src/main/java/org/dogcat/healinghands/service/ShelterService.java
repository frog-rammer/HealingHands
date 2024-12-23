package org.dogcat.healinghands.service;

import org.dogcat.healinghands.dto.ShelterDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ShelterService {
//    public ShelterDTO createShelter(ShelterDTO shelterDTO); // MultipartFile 제거
//    List<ShelterDTO> getAllShelters();
//    ShelterDTO updateShelter(String id, ShelterDTO shelterDTO);  // ID를 String 타입으로 변경
//    void deleteShelter(String id);  // ID를 String 타입으로 변경
//
    /*
     * 보호소 등록
     */

    ShelterDTO insertWaitShelter(ShelterDTO shelterDTO , MultipartFile shelterPhoto);
    public Page<ShelterDTO> getAllSheltersPageable(Pageable pageable);
    public List<ShelterDTO> getAllShelters();
    ShelterDTO getShelterById(String id);  // ID는 String 타입으로 변경
    public Page<ShelterDTO> getAllWaitingSheltersPageable(Pageable pageable);
    public Page<ShelterDTO> searchShelters(String query, Pageable pageable);
    Page<ShelterDTO> searchWaitingShelters(String query, Pageable pageable);

    boolean isUserIdExists(String userId);

    boolean isshelterNameExists(String userId);

    void approveShelterApplication(String shelterId);
    public void rejectShelterApplication(String shelterId);
    public void deleteShelter(String shelterId);
}
