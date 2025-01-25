package org.zerock.mmh.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.zerock.mmh.generator.IdGenerator;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "tbl_user_favorite")
public class UserFavorite {
    @Id
    @GenericGenerator(name = "idGenerator", strategy = "org.zerock.mmh.generator.IdGenerator",
            parameters = {@Parameter(name = IdGenerator.METHOD, value = "SEQUENCE"),
                    @Parameter(name = IdGenerator.SEQUENCENAME, value = "user_favorite_seq"),
                    @Parameter(name = IdGenerator.PREFIX, value = "UF")})
    @GeneratedValue(generator = "idGenerator")
    @Column(name = "user_favorite_id")
    private String userFavoriteId;

    @Column(length = 10, nullable = false,name = "user_fav_no")
    private String userFavNo;

    @Column(length = 10, nullable = false, name = "user_mem_no")
    private String userMemNo;

    @Column(length = 10, nullable = false, name = "manu_info_no")
    private String manuInfoNo;

    @Column(length = 10, nullable = false, name = "product_no")
    private String productNo;
}
