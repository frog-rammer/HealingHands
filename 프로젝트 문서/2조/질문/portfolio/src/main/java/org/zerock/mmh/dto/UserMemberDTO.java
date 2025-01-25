package org.zerock.mmh.dto;

import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserMemberDTO {

    private String userMemNo;

    @NotNull
    private String userMemId;

    @NotNull
    private String user_mem_pw;

    private String user_mem_mail;

    @NotNull
    private String user_mem_name;

    @NotNull
    private String user_mem_address;

    @NotNull
    private String user_mem_phone;

    private int user_mem_age;

    @NotNull
    private char user_mem_gender;

    private String user_mem_mailFull; // 조합된 이메일
    private String user_mem_mailSelect; // 선택된 도메인
    private String user_mem_mailDirect; // 직접 입력된 이메일 부분
    private String user_mem_role;

    @Getter
    @Setter
    public class LoginDTO {
        @NotNull
        private String userMemId;

        @NotNull
        private String user_mem_pw;
    }
}
