package org.zerock.mmh.service;

import org.zerock.mmh.dto.UserMemberDTO;
import org.zerock.mmh.entity.UserMember;


public interface UserMemberService {
    UserMember saveMember(UserMember userMember);

    String join(UserMemberDTO userMemberDTO);
}


