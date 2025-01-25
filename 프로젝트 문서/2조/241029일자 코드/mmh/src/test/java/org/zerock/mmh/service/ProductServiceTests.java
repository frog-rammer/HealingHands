package org.zerock.mmh.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.mmh.dto.PageRequestDTO;
import org.zerock.mmh.dto.PageResultDTO;
import org.zerock.mmh.dto.ProductDTO;
import org.zerock.mmh.entity.Product;

@SpringBootTest
public class ProductServiceTests {

    @Autowired
    private ProductService service;

    @Test
    public void testRegister() {
        ProductDTO productDTO = ProductDTO.builder()
                .product_name("test name")
                .product_size("중")
                .product_desc("test desc")
                .product_period("2일")
                .build();
        System.out.println(service.register(productDTO));
    }
//
//    @Test
//    public void testList() {
//        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(1).size(10).build();
//        PageResultDTO<ProductDTO, Product> resultDTO = service.getList(pageRequestDTO);
//
//        System.out.println("PREV: "+resultDTO.isPrev());
//        System.out.println("NEXT: "+resultDTO.isNext());
//        System.out.println("TOTAL: "+resultDTO.getTotalPage());
//
//        System.out.println("----------------------------------");
//
//        for (ProductDTO productDTO : resultDTO.getDtoList()) {
//            System.out.println(productDTO);
//        }
//        System.out.println("====================================");
//        resultDTO.getPageList().forEach(i->System.out.println(i));
//
//    }

    @Test
    public void testRemove() {
        String productNo = "P00001";
        service.removeWithImage(productNo);
    }
}
