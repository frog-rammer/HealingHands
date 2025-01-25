package org.zerock.mmh.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.zerock.mmh.dto.UserMemberDTO;
import org.zerock.mmh.entity.UserMember;
import org.zerock.mmh.repository.UserMemberRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.authentication.BadCredentialsException;

@Service
@Log4j2
@RequiredArgsConstructor
public class UserMemberServiceImpl implements UserMemberService, UserDetailsService {

    private final UserMemberRepository userMemberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserMember saveMember(UserMember userMember) {
        return userMemberRepository.save(userMember);
    }

    @Override
    public String join(UserMemberDTO userMemberDTO) {
        log.info("Joining user with details: {}", userMemberDTO);

        String email = "";

        if (userMemberDTO.getUser_mem_mailDirect() != null && !userMemberDTO.getUser_mem_mailDirect().isEmpty()) {
            email = userMemberDTO.getUser_mem_mail() + "@" + userMemberDTO.getUser_mem_mailDirect();
        } else {
            if (userMemberDTO.getUser_mem_mailSelect() == null || userMemberDTO.getUser_mem_mailSelect().isEmpty() || "none".equals(userMemberDTO.getUser_mem_mailSelect())) {
                throw new IllegalArgumentException("도메인을 선택하거나 직접 입력하세요.");
            }
            email = userMemberDTO.getUser_mem_mail() + "@" + userMemberDTO.getUser_mem_mailSelect();
        }

        if (email.isEmpty() || !email.contains("@") || !email.contains(".")) {
            log.error("Invalid email format: {}", email);
            throw new IllegalArgumentException("잘못된 이메일 형식입니다.");
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(userMemberDTO.getUser_mem_pw());

        // 엔티티 생성
        UserMember userMember = UserMember.builder()
                .userMemId(userMemberDTO.getUserMemId())
                .user_mem_pw(encodedPassword)
                .user_mem_mail(email)
                .user_mem_name(userMemberDTO.getUser_mem_name())
                .user_mem_address(userMemberDTO.getUser_mem_address())
                .user_mem_phone(userMemberDTO.getUser_mem_phone())
                .user_mem_age(userMemberDTO.getUser_mem_age())
                .user_mem_gender(userMemberDTO.getUser_mem_gender())
                .user_mem_role("USER") // 기본 역할은 USER로 설정
                .build();

        // 회원 저장 후 userMemNo 반환
        return saveMember(userMember).getUserMemNo();  // 회원 저장 후 userMemNo 반환
    }

    @Override
    public UserMember login(UserMemberDTO.LoginDTO loginDTO) {
        // 사용자 ID로 사용자 조회
        UserMember userMember = userMemberRepository.findByUserMemId(loginDTO.getUserMemId())
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        // 비밀번호가 일치하는지 확인
        if (!passwordEncoder.matches(loginDTO.getUser_mem_pw(), userMember.getUser_mem_pw())) {
            throw new BadCredentialsException("잘못된 비밀번호입니다.");
        }

        return userMember;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserMember userMember = userMemberRepository.findByUserMemId(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        return User.builder()
                .username(userMember.getUserMemId())
                .password(userMember.getUser_mem_pw())
                .roles(userMember.getUser_mem_role())
                .build();
    }
}
