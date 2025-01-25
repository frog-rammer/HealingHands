package org.zerock.mmh.service;

import org.zerock.mmh.dto.ManufacturerMemberDTO;
import org.zerock.mmh.entity.ManufacturerMember;

import java.util.List;

public interface ManufacturerMemberService {
    ManufacturerMember saveMember(ManufacturerMember manuMember);
    String join(ManufacturerMemberDTO manufacturerMemberDTO);
    ManufacturerMember login(ManufacturerMemberDTO.LoginDTO loginDTO);
    List<ManufacturerMember> findAll();
    void approveManufacturerMember(String manuMemId);
}
