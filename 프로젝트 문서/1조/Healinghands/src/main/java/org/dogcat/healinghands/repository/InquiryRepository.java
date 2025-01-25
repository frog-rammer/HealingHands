package org.dogcat.healinghands.repository;

import org.dogcat.healinghands.entity.Inquiry;
import org.dogcat.healinghands.entity.Volunteer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InquiryRepository extends JpaRepository<Inquiry, Long> {
    Page<Inquiry> findAll(Pageable pageable);
    Page<Inquiry> findByUserId(String userId, Pageable pageable);
    Page<Inquiry> findByTitleContainingOrUserIdContaining(String title, String userId, Pageable pageable);

}
