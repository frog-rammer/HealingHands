package org.zerock.mmh.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserFavoriteDTO {
    private String userFavoriteId;
    private String userFavNo;
    private String userMemNo;
    private String manuInfoNo;
    private String productNo;
}
