package org.zerock.mmh.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.zerock.mmh.entity.Product;
import org.zerock.mmh.entity.QProduct;
import org.zerock.mmh.entity.QQnaBoard;
import org.zerock.mmh.entity.QnaBoard;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class ProductRepositoryTests {

    @Autowired
    ProductRepository productRepository;

    @Test
    public void insertDummies() {
        IntStream.rangeClosed(1, 9).forEach(i -> {
            Product product = Product.builder()
                    .product_name("Name" + i)
                    .product_size("Size" + i)
                    .product_desc("Desc" + i)
                    .product_period("Period" + i)
                    .build();

            System.out.println(productRepository.save(product));
        });
    }

    @Test
    public void updateTest(){

        Optional<Product> result = productRepository.findById("P00002");//존재하는 번호로 테스트
        if(result.isPresent()){
            Product product = result.get();
            product.changeName("Changed Name....");
            product.changeDesc("Changed Desc....");
            product.changeSize("대");
            product.changePeriod("Changed Period....");
            productRepository.save(product);
        }
    }

    @Test
    public void testQuery1(){
        Pageable pageable = PageRequest.of(0,10, Sort.by("productNo").descending());
        QProduct qProduct = QProduct.product;
        String keyword = "1";
        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression expression = qProduct.product_name.contains(keyword);
        builder.and(expression);
        Page<Product> result = productRepository.findAll(builder, pageable);
        result.stream().forEach(product -> {
            System.out.println(product);
        });
    }


    @Test
    public void testQuery2() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("productNo").descending());
        QProduct qProduct = QProduct.product;
        String keyword = "대";
        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression exName = qProduct.product_name.contains(keyword);
        BooleanExpression exDesc = qProduct.product_desc.contains(keyword);
        BooleanExpression exSize = qProduct.product_size.contains(keyword);
        BooleanExpression exAll = exDesc.or(exName.or(exSize));
        builder.and(exAll);
        Page<Product> result = productRepository.findAll(builder, pageable);

        result.stream().forEach(product -> {
            System.out.println(product);
        });
    }

}
