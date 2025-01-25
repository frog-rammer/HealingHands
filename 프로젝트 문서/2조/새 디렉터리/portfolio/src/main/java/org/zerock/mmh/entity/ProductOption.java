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
@Table(name = "tbl_product_option")
public class ProductOption {
    @Id
    @GenericGenerator(name = "idGenerator", strategy = "org.zerock.mmh.generator.IdGenerator",
            parameters = {@Parameter(name = IdGenerator.METHOD, value = "SEQUENCE"),
                    @Parameter(name = IdGenerator.SEQUENCENAME, value = "product_option_seq"),
                    @Parameter(name = IdGenerator.PREFIX, value = "PO")})
    @GeneratedValue(generator = "idGenerator")
    @Column(name = "pro_option_no")
    private String proOptionNo;

    @Column(length = 10, nullable = false, name="product_no")
    private String productNo;

    @Column(length = 10, nullable = false,name = "option_no")
    private String optionNo;

    @Column(length = 10, nullable = false, name="manu_info_no")
    private String manuInfoNo;

    @Column(nullable = true, name = "pro_option_price")
    private int proOptionPrice;
}
