package org.zerock.mmh.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.zerock.mmh.entity.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, String>, QuerydslPredicateExecutor<Product> {

    @Query("select m, pi from Product m " +
            "left outer join ProductImage pi on pi.product = m")
    Page<Object[]> getListPage(Pageable pageable);

    @Query("select m, pi from Product m " + "left outer join ProductImage pi on pi.product = m " +
            "where m.productNo = :productNo group by pi")
    List<Object[]> getProductWithAll(String productNo);
}
