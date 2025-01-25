package org.zerock.mmh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.mmh.entity.UserMember;

import java.util.Optional;

public interface UserMemberRepository extends JpaRepository<UserMember, String> {
    Optional<UserMember> findByUserMemId(String userMemId);

}

