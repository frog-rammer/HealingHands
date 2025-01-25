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
@Table(name = "tbl_user_member")
public class UserMember {

    @Id
    @GenericGenerator(name = "idGenerator", strategy = "org.zerock.mmh.generator.IdGenerator",
            parameters = {@Parameter(name = IdGenerator.METHOD, value = "SEQUENCE"),
                    @Parameter(name = IdGenerator.SEQUENCENAME, value = "user_member_seq"),
                    @Parameter(name = IdGenerator.PREFIX, value = "UM")})
    @GeneratedValue(generator = "idGenerator")
    @Column(name="user_mem_no")
    private String userMemNo;

    @Column(length = 20, nullable = false)
    private String user_mem_id;

    @Column(length = 100, nullable = false)
    private String user_mem_pw;

    @Column(length = 50, nullable = false)
    private String user_mem_mail;

    @Column(length = 20, nullable = false)
    private String user_mem_name;

    @Column(length = 100, nullable = false)
    private String user_mem_address;

    @Column(length = 20, nullable = false)
    private String user_mem_phone;

    @Column(nullable = false)
    private int user_mem_age;

    @Column(nullable = false)
    private char user_mem_gender;
}
