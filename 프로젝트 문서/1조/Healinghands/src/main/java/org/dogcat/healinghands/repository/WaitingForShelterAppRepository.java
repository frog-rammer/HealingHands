package org.dogcat.healinghands.repository;

import org.dogcat.healinghands.entity.WaitingForShelterApp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WaitingForShelterAppRepository extends JpaRepository<WaitingForShelterApp, String> {
    Page<WaitingForShelterApp> findByShelterNameContainingIgnoreCase(String name, Pageable pageable);
}
