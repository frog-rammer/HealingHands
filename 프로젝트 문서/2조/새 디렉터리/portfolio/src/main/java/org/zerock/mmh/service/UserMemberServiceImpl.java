package org.zerock.mmh.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.zerock.mmh.dto.UserMemberDTO;
import org.zerock.mmh.entity.UserMember;
import org.zerock.mmh.repository.UserMemberRepository;

@Service
@Log4j2
@RequiredArgsConstructor
public class UserMemberServiceImpl implements UserMemberService {

    private final UserMemberRepository userMemberRepository;

    @Override
    public  UserMember saveMember(UserMember userMember){
        return userMemberRepository.save(userMember);
    }

    @Override
    public String join(UserMemberDTO userMemberDTO) {
        UserMember userMember = UserMember.builder()
                .user_mem_id(userMemberDTO.getUserMemId())
                .user_mem_mail(userMemberDTO.getUserMemMail())
                .user_mem_name(userMemberDTO.getUserMemName())
                .user_mem_address(userMemberDTO.getUserMemAddress())
                .user_mem_phone(userMemberDTO.getUserMemPhone())
                .user_mem_age(userMemberDTO.getUserMemAge())
                .user_mem_gender(userMemberDTO.getUserMemGender())
                .build();


        return saveMember(userMember).getUserMemNo();

    }


}
