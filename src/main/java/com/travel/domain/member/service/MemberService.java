package com.travel.domain.member.service;

import com.travel.domain.member.dao.MemberRepository;
import com.travel.domain.member.dto.MemberDto;
import com.travel.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public MemberDto getMemberProfile(Long memberId) {
        Member member = memberRepository.findByIdOrElseThrow(memberId);

        return MemberDto.of(member);
    }
}
