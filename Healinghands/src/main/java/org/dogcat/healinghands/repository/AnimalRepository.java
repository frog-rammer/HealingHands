package org.dogcat.healinghands.repository;

import org.dogcat.healinghands.dto.AnimalDTO;
import org.dogcat.healinghands.entity.Animal;
import org.dogcat.healinghands.entity.WaitingForShelterApp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long>, JpaSpecificationExecutor<Animal>  {
    // 기본 CRUD 메서드들은 JpaRepository에 이미 포함되어 있음
    Page<Animal> findAll(Pageable pageable);
    
    //주석

    @Query("SELECT a FROM Animal a LEFT JOIN a.shelter s " +
            "WHERE (:shelterName IS NULL OR :shelterName = '' OR s.shelterName LIKE %:shelterName%) " +
            "AND ((:species IS NULL OR :#{#species.isEmpty()} = true) OR a.species IN :species) " +
            "AND (:breed IS NULL OR :breed = '' OR a.breed LIKE %:breed%)")
    Page<Animal> findByFilters(@Param("shelterName") String shelterName,
                               @Param("species") List<String> species,
                               @Param("breed") String breed,
                               Pageable pageable);
    // shelterId로 동물 목록 조회
    List<Animal> findByShelterId(String shelterId);
    List<Animal> findTop5ByOrderByCreatedAtDesc();

}


