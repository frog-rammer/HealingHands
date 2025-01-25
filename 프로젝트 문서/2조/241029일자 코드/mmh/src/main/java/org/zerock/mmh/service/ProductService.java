package org.zerock.mmh.service;

import org.zerock.mmh.dto.*;
import org.zerock.mmh.entity.Product;
import org.zerock.mmh.entity.ProductImage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface ProductService {
    String register(ProductDTO dto);

    //    PageResultDTO<ProductDTO, Product> getList(PageRequestDTO requestDTO);
    PageResultDTO<ProductDTO, Object[]> getList(PageRequestDTO requestDTO);

    //    ProductDTO read(String productNo);
    ProductDTO getProduct(String productNo);

    void remove(String productNo);

    void removeWithImage(String productNo); //삭제 기능

    void modify(ProductDTO dto);


    //이미지 업로드 구현중
    default Map<String, Object> dtoToEntity(ProductDTO dto) { //Map타입으로 변환

        Map<String, Object> entityMap = new HashMap<>();

        Product product = Product.builder()
                .productNo(dto.getProductNo())
                .product_name(dto.getProduct_name())
                .product_size(dto.getProduct_size())
                .product_desc(dto.getProduct_desc())
                .product_period(dto.getProduct_period())
                .build();

        entityMap.put("product", product);

        List<ProductImageDTO> imageDTOList = dto.getImageDTOList();

        //ProductImageDTO 처리
        if (imageDTOList != null && imageDTOList.size() > 0) {
            List<ProductImage> productImageList = imageDTOList.stream().map(productImageDTO -> {
                ProductImage productImage = ProductImage.builder()
                        .path(productImageDTO.getPath())
                        .imgName(productImageDTO.getImgName())
                        .uuid(productImageDTO.getUuid())
                        .product(product)
                        .build();
                return productImage;
            }).collect(Collectors.toList());

            entityMap.put("imgList", productImageList);
        }
        return entityMap;

    }

    //
////이미지 업로드 구현전
//    default Product dtoToEntity(ProductDTO dto) {
//        Product entity = Product.builder()
//                .productNo(dto.getProductNo())
//                .product_name(dto.getProduct_name())
//                .product_thumbnail(dto.getProduct_thumbnail())
//                .product_size(dto.getProduct_size())
//                .product_desc(dto.getProduct_desc())
//                .product_period(dto.getProduct_period())
//                .build();
//        return entity;
//}
    default ProductDTO entityToDto(Product entity) {
        ProductDTO dto = ProductDTO.builder()
                .productNo(entity.getProductNo())
                .product_name(entity.getProduct_name())
                .product_size(entity.getProduct_size())
                .product_desc(entity.getProduct_desc())
                .product_period(entity.getProduct_period())
                .build();
        return dto;
    }

    default ProductDTO entitiesToDTO(Product product, List<ProductImage> productImages) {
        ProductDTO productDTO = ProductDTO.builder()
                .productNo(product.getProductNo())
                .product_name(product.getProduct_name())
                .product_size(product.getProduct_size())
                .product_desc(product.getProduct_desc())
                .product_period(product.getProduct_period())
                .build();
        try {
            if (productImages != null && productImages.size() > 0) {
                List<ProductImageDTO> productImageDTOList = productImages.stream().map(productImage -> {
                    return ProductImageDTO.builder()
                            .imgName(productImage.getImgName())
                            .path(productImage.getPath())
                            .uuid(productImage.getUuid())
                            .build();
                }).collect(Collectors.toList());
                productDTO.setImageDTOList(productImageDTOList);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return productDTO;
    }
}
