package org.zerock.mmh.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.zerock.mmh.generator.IdGenerator;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "tbl_manufacturer_member")
public class ManufacturerMember {

    @Id
    @GenericGenerator(name = "idGenerator", strategy = "org.zerock.mmh.generator.IdGenerator",
            parameters = {@Parameter(name = IdGenerator.METHOD, value = "SEQUENCE"),
                    @Parameter(name = IdGenerator.SEQUENCENAME, value = "manufacturer_member_seq"),
                    @Parameter(name = IdGenerator.PREFIX, value = "MM")})
    @GeneratedValue(generator = "idGenerator")
    @Column(name = "manu_mem_no")
    private String manuMemNo;

    @Column(length = 20, nullable = false)
    private String manu_mem_id;

    @Column(length = 100, nullable = false)
    private String manu_mem_pw;

    @Column(length = 50, nullable = false)
    private String manu_mem_mail;

    @Column(length = 20, nullable = false)
    private String manu_mem_name;

    @Column(length = 10, nullable = false)
    private String manu_mem_bnumber;

    @Column(nullable = false)
    @ColumnDefault("'0'")
    private int manu_mem_approval;
}
