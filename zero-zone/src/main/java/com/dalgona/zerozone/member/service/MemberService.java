package com.dalgona.zerozone.member.service;

import com.dalgona.zerozone.common.exception.BadRequestErrorCode;
import com.dalgona.zerozone.common.exception.BadRequestException;
import com.dalgona.zerozone.member.domain.Member;
import com.dalgona.zerozone.member.domain.MemberRepository;
import com.dalgona.zerozone.member.dto.MemberSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    public final MemberRepository memberRepository;
    private final EmailAuthService emailAuthService;
    private final PasswordEncoder passwordEncoder;

    public Boolean checkEmailDuplicate(String email) {
        return memberRepository.existsByEmail(email);
    }

    @Transactional
    public void register(MemberSaveRequestDto memberSaveRequestDto) throws BadRequestException {
        String email = memberSaveRequestDto.getEmail();
        checkEmailDuplicateThenThrow(email);
        emailAuthService.checkEmailAuthed(email);
        Member member = memberSaveRequestDto.toEntity(passwordEncoder);
        memberRepository.save(member);
    }

    private void checkEmailDuplicateThenThrow(String email) throws BadRequestException {
        if(checkEmailDuplicate(email))
            throw new BadRequestException(BadRequestErrorCode.DUPLICATED, "해당 이메일로 이미 회원가입했습니다.");
    }
}
