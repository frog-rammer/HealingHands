package org.zerock.mmh.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ManufacturerMemberDTO {

    private String manuMemNo;
    private String manuMemId;
    private String manu_mem_pw;
    private String manu_mem_mail;
    private String manu_mem_name;
    private String manu_mem_bnumber;
    private int manu_mem_approval;

    private String manu_mem_mailFull; // 조합된 이메일
    private String manu_mem_mailSelect; // 선택된 도메인
    private String manu_mem_mailDirect; // 직접 입력된 이메일 부분
    private String manu_mem_role;
    private boolean superAdmin; // 필드 이름 수정


    public boolean isSuperAdmin() {
        return superAdmin;
    }

    @Data
    public static class LoginDTO {
        private String manuMemId;
        private String manu_mem_pw;
    }
}
