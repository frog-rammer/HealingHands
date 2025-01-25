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
@Table(name = "tbl_order_board")
public class OrderBoard {
    @Id
    @GenericGenerator(name = "idGenerator", strategy = "org.zerock.mmh.generator.IdGenerator",
            parameters = {@Parameter(name = IdGenerator.METHOD, value = "SEQUENCE"),
                    @Parameter(name = IdGenerator.SEQUENCENAME, value = "order_board_seq"),
                    @Parameter(name = IdGenerator.PREFIX, value = "OB")})
    @GeneratedValue(generator = "idGenerator")
    @Column(name = "order_board_no")
    private String orderBoardNo;

    @Column(length = 10, nullable = false, name="product_no")
    private String productNo;

    @Column(nullable = false)
    private int order_board_quantity;

    @Column(nullable = false)
    private String order_board_ddate;

    @Column(length = 500, nullable = false)
    private String order_board_desc;

    @Column(length = 10, nullable = false, name = "user_mem_no")
    private String userMemNo;

    @Column(length = 10, nullable = false, name = "manu_info_no")
    private String manuInfoNo;

    @Column(length = 500, nullable = false)
    private String order_board_answer;
}

