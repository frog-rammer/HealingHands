package org.zerock.mmh.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductDTO {
    private String productNo;
    private String product_name;
    private String product_size;
    private String product_desc;
    private String product_period;

    @Builder.Default
    private List<ProductImageDTO> imageDTOList = new ArrayList<>();
}
