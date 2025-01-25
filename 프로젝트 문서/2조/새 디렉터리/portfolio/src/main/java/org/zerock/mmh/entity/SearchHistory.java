package org.zerock.mmh.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.zerock.mmh.generator.IdGenerator;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "tbl_search_history")
public class SearchHistory {
    @Id
    @GenericGenerator(name = "idGenerator", strategy = "org.zerock.mmh.generator.IdGenerator",
            parameters = {@Parameter(name = IdGenerator.METHOD, value = "SEQUENCE"),
                    @Parameter(name = IdGenerator.SEQUENCENAME, value = "user_favorite_seq"),
                    @Parameter(name = IdGenerator.PREFIX, value = "SH")})
    @GeneratedValue(generator = "idGenerator")
    @Column(name = "search_history_no")
    private String searchHistoryNo;

    @Column(length = 10, nullable = false, name="user_mem_no")
    private String userMemNo;

    @Column(length = 10, nullable = true,name = "manu_info_no")
    private String manuInfoNo;

    @Column(length = 10, nullable = true, name = "product_no")
    private String productNo;

    @Column(length = 20, nullable = true)
    private String search_history_keyword;

    @Column(nullable = false)
    private LocalDateTime search_history_date;
}
