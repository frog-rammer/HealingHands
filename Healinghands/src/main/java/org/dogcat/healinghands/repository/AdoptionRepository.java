package org.dogcat.healinghands.repository;

import org.dogcat.healinghands.entity.Adoption;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdoptionRepository extends JpaRepository<Adoption, Long> {
    List<Adoption> findAllByName(String name); // 이름으로 검색하는 메서드
    // shelterId로 입양 신청자 목록 페이징 조회
    Page<Adoption> findByShelterId(String shelterId, Pageable pageable);
}
