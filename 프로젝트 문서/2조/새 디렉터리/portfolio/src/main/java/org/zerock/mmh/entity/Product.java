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
@Table(name = "tbl_product")
public class Product {
    @Id
    @GenericGenerator(name = "idGenerator", strategy = "org.zerock.mmh.generator.IdGenerator",
            parameters = {@Parameter(name = IdGenerator.METHOD, value = "SEQUENCE"),
                    @Parameter(name = IdGenerator.SEQUENCENAME, value = "product_seq"),
                    @Parameter(name = IdGenerator.PREFIX, value = "P")})
    @GeneratedValue(generator = "idGenerator")
    @Column(name = "product_no")
    private String productNo;

    @Column(length = 20, nullable = false)
    private String product_name;

    @Column(length = 100, nullable = true)
    private String product_thumbnail;

    @Column(length = 10, nullable = false)
    private String product_size;

    @Column(length = 500, nullable = true)
    private String product_desc;

    @Column(length = 20, nullable = true)
    private String product_period;


}
