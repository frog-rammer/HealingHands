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
@Table(name = "tbl_manufacturer_service")
public class ManufacturerService {
    @Id
    @GenericGenerator(name = "idGenerator", strategy = "org.zerock.mmh.generator.IdGenerator",
            parameters = {@Parameter(name = IdGenerator.METHOD, value = "SEQUENCE"),
                    @Parameter(name = IdGenerator.SEQUENCENAME, value = "user_favorite_seq"),
                    @Parameter(name = IdGenerator.PREFIX, value = "MS")})
    @GeneratedValue(generator = "idGenerator")
    @Column(name="manu_service_no")
    private String manuServiceNo;

    @Column(length = 10, nullable = false, name = "manu_info_no")
    private String manuInfoNo;

    @Column(length = 10, nullable = false, name = "service_no")
    private String serviceNo;

}
