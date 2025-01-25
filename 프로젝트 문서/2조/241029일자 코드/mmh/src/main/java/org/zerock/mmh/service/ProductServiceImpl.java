package org.zerock.mmh.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.zerock.mmh.dto.PageRequestDTO;
import org.zerock.mmh.dto.PageResultDTO;
import org.zerock.mmh.dto.ProductDTO;
import org.zerock.mmh.entity.Product;
import org.zerock.mmh.entity.ProductImage;
import org.zerock.mmh.repository.ProductImageRepository;
import org.zerock.mmh.repository.ProductRepository;

import java.util.*;
import java.util.function.Function;
import java.io.File;

@Service
@Log4j2
@RequiredArgsConstructor //자동주입
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductImageRepository imageRepository;

    @Value("${org.zerock.upload.path}") //application.properties의 변수
    private String uploadPath;

    //이미지 업로드 구현중
    @Transactional
    @Override
    public String register(ProductDTO productDTO) {

        Map<String, Object> entityMap = dtoToEntity(productDTO);
        Product product = (Product) entityMap.get("product");
        List<ProductImage> productImageList = (List<ProductImage>) entityMap.get("imgList");
        productRepository.save(product);
        if (productImageList != null && productImageList.size() > 0) {
            productImageList.forEach(productImage -> {
                imageRepository.save(productImage);
            });
        }
        return product.getProductNo();
    }

//   // 이미지 업로드 구현 전
//    public String register(ProductDTO dto) {
//        log.info("DTO......");
//        log.info(dto);
//        Product entity = dtoToEntity(dto);
//
//        log.info(entity);
//        productRepository.save(entity);
//
//        return entity.getProductNo();
//    }
//    @Override
//    public PageResultDTO<ProductDTO, Product> getList(PageRequestDTO requestDTO) {
//        Pageable pageable = requestDTO.getPageable(Sort.by("productNo").descending());
//        Page<Product> result = productRepository.findAll(pageable);
//
//        Function<Product, ProductDTO> fn = (entity -> entityToDto(entity));
//        return new PageResultDTO<>(result, fn);
//    }

    @Override
    public PageResultDTO<ProductDTO, Object[]> getList(PageRequestDTO requestDTO) {

        Pageable pageable = requestDTO.getPageable(Sort.by("productNo").descending());

        Page<Object[]> result = productRepository.getListPage(pageable);

        log.info("==============================================");
        result.getContent().forEach(arr -> {
            log.info(Arrays.toString(arr));
        });


        Function<Object[], ProductDTO> fn = (arr -> entitiesToDTO(
                (Product) arr[0],
                (List<ProductImage>) (Arrays.asList((ProductImage) arr[1]))
        ));

        return new PageResultDTO<>(result, fn);
    }

    public ProductDTO getProduct(String productNo) {
        List<Object[]> result = productRepository.getProductWithAll(productNo);
        Product product = (Product) result.get(0)[0]; //Product 엔티티는 가장 앞에 존재 - 모든 Row가 동일한 값
        List<ProductImage> productImageList = new ArrayList<>(); //상품 이미지 갯수 만큼 ProductImage 객체 필요
        result.forEach(arr -> {
            ProductImage productImage = (ProductImage) arr[1];
            productImageList.add(productImage);
        });
        return entitiesToDTO(product, productImageList);
    }

// //getProduct로 변환
//    @Override
//    public ProductDTO read(String productNo) {
//        Optional<Product> result = productRepository.findById(productNo);
//        return result.isPresent() ? entityToDto(result.get()) : null;
//    }

    @Override
    public void remove(String productNo) {
        productRepository.deleteById(productNo);
    }

    @Transactional
    @Override
    public void removeWithImage(String productNo) { //삭제 구현, 트랜잭션 추가
        //이미지부터 삭제
        List<ProductImage> productImageList = imageRepository.findByProductNo(productNo);
        productImageList.forEach(productImage -> {
            try {
                File file = new File(uploadPath + File.separator + productImage.getPath() + File.separator + productImage.getUuid() + "_" + productImage.getImgName());
                boolean result = file.delete();
                File thumbnail = new File(file.getParent(), "s_" + file.getName());
                result = thumbnail.delete();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        imageRepository.deleteByProductNo(productNo);
        //상품 삭제
        productRepository.deleteById(productNo);
    }


    //    @Override
//    public void modify(ProductDTO dto) {
//        //업데이트 하는 항목은 '카테고리', '제목', '내용'
//
//        Optional<Product> result = productRepository.findById(dto.getProductNo());
//        if (result.isPresent()) {
//            Product entity = result.get();
//
//            entity.changeName(dto.getProduct_name());
//            entity.changePeriod(dto.getProduct_period());
//            entity.changeSize(dto.getProduct_size());
//            entity.changeDesc(dto.getProduct_desc());
//            productRepository.save(entity);
//        }
//    }
    @Transactional
    @Override
    public void modify(ProductDTO productDTO) {
        //업데이트 하는 항목은 '카테고리', '제목', '내용'

        Optional<Product> result = productRepository.findById(productDTO.getProductNo());
        if (result.isPresent()) {
            Product entity = result.get();

            entity.changeName(productDTO.getProduct_name());
            entity.changePeriod(productDTO.getProduct_period());
            entity.changeSize(productDTO.getProduct_size());
            entity.changeDesc(productDTO.getProduct_desc());

            Map<String, Object> entityMap = dtoToEntity(productDTO);
            List<ProductImage> productImageList = (List<ProductImage>) entityMap.get("imgList");

            if (productImageList != null) {
                imageRepository.deleteByProductNo(productDTO.getProductNo());
                productImageList.forEach(productImage -> {
                    imageRepository.save(productImage);
                });
            }
            productRepository.save(entity);
        }
    }

}
