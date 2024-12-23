package org.dogcat.healinghands.repository;

import org.dogcat.healinghands.entity.Shelter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShelterRepository extends JpaRepository<Shelter, String> {
    boolean existsByshelterName(String shelterName);
    Shelter findByShelterId(String shelterId);
    Page<Shelter> findByShelterNameContainingIgnoreCase(String name, Pageable pageable);
    List<Shelter> findAll();
}
