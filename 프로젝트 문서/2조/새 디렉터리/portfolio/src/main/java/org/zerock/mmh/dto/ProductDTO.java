package org.zerock.mmh.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductDTO {
    private String productNo;
    private String productName;       // Changed to camelCase
    private String productThumbnail;   // Changed to camelCase
    private String productSize;        // Changed to camelCase
    private String productDesc;        // Changed to camelCase
    private String productPeriod;      // Changed to camelCase

}
