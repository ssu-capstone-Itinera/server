package com.travel.domain.member.service;

import com.travel.domain.member.dao.MemberRepository;
import com.travel.domain.member.dto.MemberDto;
import com.travel.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberDtoService {
    private final MemberRepository memberRepository;

    public MemberDto getMemberDto(Long memberID){
        Member member = memberRepository.findByIdOrElseThrow(memberID);

        return MemberDto.of(member);
    }
}
