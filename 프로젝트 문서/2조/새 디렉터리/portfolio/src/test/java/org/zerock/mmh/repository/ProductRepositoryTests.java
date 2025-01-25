package org.zerock.mmh.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.mmh.entity.Product;

import java.util.stream.IntStream;

@SpringBootTest
public class ProductRepositoryTests {
    @Autowired
    private ProductRepository repository;
    @Autowired
    private ProductRepository productRepository;

    @Test
    public void insertDummies(){
        IntStream.rangeClosed(1,2).forEach(i->{
            Product product = Product.builder()
                    .product_name("PN" + i)
                    .product_thumbnail("PT"+i)
                    .product_size("PS"+i)
                    .product_desc("PD"+i)
                    .product_period("PP"+i)
                    .build();
            System.out.println(productRepository.save(product));
        });
    }

}