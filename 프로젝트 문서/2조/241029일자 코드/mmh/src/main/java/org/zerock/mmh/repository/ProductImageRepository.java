package org.zerock.mmh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.zerock.mmh.entity.ProductImage;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    @Modifying
    @Query("delete from ProductImage pi where pi.product.productNo =:productNo ")
    void deleteByProductNo(String productNo);

    @Query("select pi from ProductImage pi where pi.product.productNo =:productNo ")
    List<ProductImage> findByProductNo(String productNo);
}
