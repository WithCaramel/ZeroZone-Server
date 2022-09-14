package com.dalgona.zerozone.member.service;

import com.dalgona.zerozone.member.domain.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    public final MemberRepository memberRepository;
    public Boolean checkEmailDuplicate(String email) {
        return memberRepository.existsByEmail(email);
    }
}
