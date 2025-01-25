package org.zerock.mmh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.mmh.entity.ManufacturerMember;


import java.util.Optional;

public interface ManufacturerMemberRepository extends JpaRepository<ManufacturerMember, String> {
    Optional<ManufacturerMember> findByManuMemId(String manuMemId);

}




