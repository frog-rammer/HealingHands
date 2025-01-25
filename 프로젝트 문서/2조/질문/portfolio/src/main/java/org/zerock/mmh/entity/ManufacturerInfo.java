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
@Table(name = "tbl_manufacturer_info")
public class ManufacturerInfo {

    @Id
    @GenericGenerator(name = "idGenerator", strategy = "org.zerock.mmh.generator.IdGenerator",
            parameters = {@Parameter(name = IdGenerator.METHOD, value = "SEQUENCE"),
                    @Parameter(name = IdGenerator.SEQUENCENAME, value = "manufacturer_info_seq"),
                    @Parameter(name = IdGenerator.PREFIX, value = "MI")})
    @GeneratedValue(generator = "idGenerator")
    @Column(name="manu_info_no")
    private String manuInfoNo;

    @Column(length = 20, nullable = false)
    private String manu_info_name;

    @Column(length = 100, nullable = false)
    private String manu_info_site;

    @Column(length = 10, nullable = false, name = "manu_mem_no")
    private String manuMemNo;

    private int Bookmark = 0;
}
