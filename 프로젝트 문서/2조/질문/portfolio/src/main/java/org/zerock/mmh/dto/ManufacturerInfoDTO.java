package org.zerock.mmh.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ManufacturerInfoDTO {
    private String manuInfoNo;
    private String manuInfoName;
    private String manuInfoSite;
    private String manuMemNo;

}
