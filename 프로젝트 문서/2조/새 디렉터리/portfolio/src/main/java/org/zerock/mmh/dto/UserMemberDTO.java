package org.zerock.mmh.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserMemberDTO {

    private String userMemNo;
    private String userMemId;
    private String userMemMail;
    private String userMemName;
    private String userMemAddress;
    private String userMemPhone;
    private int userMemAge;
    private char userMemGender;
}
