package org.dogcat.healinghands.repository;

import org.dogcat.healinghands.entity.Donation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DonationRepository extends JpaRepository<Donation, Long> {
    Page<Donation> findAll(Pageable pageable);
    Page<Donation> findByUserId(String userId, Pageable pageable);
    Page<Donation> findByShelterName(String shelterName, Pageable pageable);
}
