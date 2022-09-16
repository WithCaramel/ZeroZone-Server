package com.dalgona.zerozone.member.controller;

import com.dalgona.zerozone.common.response.OnlyResponseString;
import com.dalgona.zerozone.member.domain.Member;
import com.dalgona.zerozone.member.dto.MemberLoginRequestDto;
import com.dalgona.zerozone.member.dto.MemberSaveRequestDto;
import com.dalgona.zerozone.member.service.MemberService;
import com.dalgona.zerozone.security.dto.TokenDto;
import com.dalgona.zerozone.security.dto.TokenRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/{email}")
    public Boolean checkEmailDuplicate(@PathVariable String email){
        return memberService.checkEmailDuplicate(email);
    }

    @PostMapping("/members")
    public OnlyResponseString signup(@RequestBody @Valid MemberSaveRequestDto memberSaveRequestDto){
        memberService.register(memberSaveRequestDto);
        return new OnlyResponseString("회원가입에 성공했습니다.");
    }

    @PostMapping("/members/login")
    public TokenDto login(@RequestBody @Valid MemberLoginRequestDto requestDto){
        return memberService.login(requestDto.getEmail(), requestDto.getPassword());
    }

    @PostMapping("/members/token/reissue")
    public TokenDto reissue(@RequestBody TokenRequestDto dto){
        return memberService.reissue(dto);
    }

}
