package org.zerock.mmh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.mmh.entity.UserMember;

public interface UserMemberRepository extends JpaRepository<UserMember, String> {
}
