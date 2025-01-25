package org.dogcat.healinghands.repository;

import org.dogcat.healinghands.entity.DailyVisitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface DailyVisitorRepository extends JpaRepository<DailyVisitor, Long> {
    DailyVisitor findByDate(LocalDate date);
}
