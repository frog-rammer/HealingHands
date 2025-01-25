package org.zerock.mmh.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.zerock.mmh.dto.ManufacturerMemberDTO;
import org.zerock.mmh.entity.ManufacturerMember;
import org.zerock.mmh.repository.ManufacturerMemberRepository;

import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class ManufacturerMemberServiceImpl implements ManufacturerMemberService, UserDetailsService {

    private final ManufacturerMemberRepository manufacturerMemberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ManufacturerMember saveMember(ManufacturerMember manufacturerMember) {
        return manufacturerMemberRepository.save(manufacturerMember);
    }

    @Override
    @Transactional
    public String join(ManufacturerMemberDTO manufacturerMemberDTO) {
        log.info("Joining user with details: {}", manufacturerMemberDTO);
        String email = createEmail(manufacturerMemberDTO);
        validateEmail(email);
        String encodedPassword = passwordEncoder.encode(manufacturerMemberDTO.getManu_mem_pw());

        String role = manufacturerMemberDTO.isSuperAdmin() ? "SUPER_ADMIN" : "FACTURER"; // 조건에 따라 역할 결정

        ManufacturerMember manufacturerMember = ManufacturerMember.builder()
                .manuMemId(manufacturerMemberDTO.getManuMemId())
                .manu_mem_pw(encodedPassword)
                .manu_mem_mail(email)
                .manu_mem_name(manufacturerMemberDTO.getManu_mem_name())
                .manu_mem_bnumber(manufacturerMemberDTO.getManu_mem_bnumber())
                .manu_mem_approval(manufacturerMemberDTO.getManu_mem_approval())
                .manu_mem_role(role) //
                .build();

        return saveMember(manufacturerMember).getManuMemNo();
    }


    @Override
    public ManufacturerMember login(ManufacturerMemberDTO.LoginDTO loginDTO) {
        ManufacturerMember manufacturerMember = manufacturerMemberRepository.findById(loginDTO.getManuMemId())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        if (!passwordEncoder.matches(loginDTO.getManu_mem_pw(), manufacturerMember.getManu_mem_pw())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }

        if (manufacturerMember.getManu_mem_approval() != 1) {
            throw new IllegalArgumentException("회원 가입 승인 대기 중입니다. 관리자에게 문의하십시오.");
        }

        return manufacturerMember;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ManufacturerMember manufacturerMember = manufacturerMemberRepository.findByManuMemId(username)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        return User.builder()
                .username(manufacturerMember.getManuMemId())
                .password(manufacturerMember.getManu_mem_pw())
                .roles(manufacturerMember.getManu_mem_role())
                .build();
    }

    @Override
    public List<ManufacturerMember> findAll() {
        return manufacturerMemberRepository.findAll();
    }

    @Override
    public void approveManufacturerMember(String manuMemId) {
        ManufacturerMember manufacturerMember = manufacturerMemberRepository.findById(manuMemId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        manufacturerMember.setManu_mem_approval(1);
        manufacturerMemberRepository.save(manufacturerMember);
    }

    private String createEmail(ManufacturerMemberDTO manufacturerMemberDTO) {
        if (manufacturerMemberDTO.getManu_mem_mailDirect() != null && !manufacturerMemberDTO.getManu_mem_mailDirect().isEmpty()) {
            return manufacturerMemberDTO.getManu_mem_mail() + "@" + manufacturerMemberDTO.getManu_mem_mailDirect();
        } else {
            if (manufacturerMemberDTO.getManu_mem_mailSelect() == null || manufacturerMemberDTO.getManu_mem_mailSelect().isEmpty() || "none".equals(manufacturerMemberDTO.getManu_mem_mailSelect())) {
                throw new IllegalArgumentException("도메인을 선택하거나 직접 입력하세요.");
            }
            return manufacturerMemberDTO.getManu_mem_mail() + "@" + manufacturerMemberDTO.getManu_mem_mailSelect();
        }
    }

    private void validateEmail(String email) {
        if (email.isEmpty() || !email.contains("@") || !email.contains(".")) {
            log.error("Invalid email format: {}", email);
            throw new IllegalArgumentException("잘못된 이메일 형식입니다.");
        }
    }
}
