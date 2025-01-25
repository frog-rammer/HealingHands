package org.dogcat.healinghands.repository;

import org.dogcat.healinghands.entity.Inquiry;
import org.dogcat.healinghands.entity.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.dogcat.healinghands.entity.Shelter;


import java.util.List;


public interface VolunteerRepository extends JpaRepository<Volunteer, Long> {
    Page<Volunteer> findAll(Pageable pageable);
    List<Volunteer> findByShelterId(String shelterId);

    @Query("SELECT v FROM Volunteer v JOIN v.shelter s WHERE v.title LIKE %:title% OR s.shelterName LIKE %:shelterName%")
    Page<Volunteer> findByTitleContainingOrShelterNameContaining(@Param("title") String title, @Param("shelterName") String shelterName, Pageable pageable);

}
