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
@Table(name = "tbl_option")
public class Option {
    @Id
    @GenericGenerator(name = "idGenerator", strategy = "org.zerock.mmh.generator.IdGenerator",
            parameters = {@Parameter(name = IdGenerator.METHOD, value = "SEQUENCE"),
                    @Parameter(name = IdGenerator.SEQUENCENAME, value = "option_seq"),
                    @Parameter(name = IdGenerator.PREFIX, value = "O")})
    @GeneratedValue(generator = "idGenerator")
    @Column(name = "option_no")
    private String optionNo;

    @Column(length = 20, nullable = false)
    private String option_name;

    @Column(length = 100, nullable = true)
    private String option_thumbnail;

    @Column(length = 500, nullable = true)
    private String option_desc;

}
