package org.zerock.mmh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.zerock.mmh.entity.QnaBoard;

public interface QnaBoardRepository extends JpaRepository<QnaBoard, String> , QuerydslPredicateExecutor<QnaBoard> {
}
