package org.zerock.mmh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.zerock.mmh.entity.UserFavorite;

public interface UserFavoriteRepository extends JpaRepository<UserFavorite, String>,  QuerydslPredicateExecutor<UserFavorite> {
}
