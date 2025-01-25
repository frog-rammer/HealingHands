package org.zerock.mmh.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ManufacturerInfoDTO {
    private String manuInfoNo;       // Identifier for the manufacturer
    private String manuInfoName;     // Changed to camelCase
    private String manuInfoSite;     // Changed to camelCase
    private String manuMemNo;        // Identifier for the member
    private int bookmark = 0;             // Changed field name to camelCase
}
