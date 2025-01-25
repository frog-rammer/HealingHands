package org.zerock.mmh.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "tbl_user_member")
public class UserMember {

    @Id
    @Column(name = "user_mem_no", columnDefinition = "CHAR(36)")
    private String userMemNo;

    @Column(length = 20, nullable = false, name = "user_mem_id")
    private String userMemId;

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

    private String user_mem_role;

    @PrePersist
    public void generateId() {
        if (this.userMemNo == null) {
            this.userMemNo = UUID.randomUUID().toString().replace("-", "");
        }
    }

}
