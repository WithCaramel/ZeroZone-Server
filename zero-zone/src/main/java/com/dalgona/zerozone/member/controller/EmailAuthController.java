package com.dalgona.zerozone.member.controller;

import com.dalgona.zerozone.common.response.OnlyResponseString;
import com.dalgona.zerozone.member.dto.EmailSendResponseDto;
import com.dalgona.zerozone.member.dto.EmailUserAuthRequestDto;
import com.dalgona.zerozone.member.dto.EmailUserValidRequestDto;
import com.dalgona.zerozone.member.service.EmailAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class EmailAuthController {

    private final EmailAuthService emailAuthService;

    @PostMapping("/members/auth-code/send")
    public EmailSendResponseDto sendAuthCode(@RequestBody @Valid EmailUserAuthRequestDto requestDto){
        emailAuthService.requestEmailVerify(requestDto.getEmail());
        return EmailSendResponseDto.of(requestDto.getEmail(), "이메일 전송에 성공했습니다.");
    }

    @PatchMapping("/members/auth-code/verify")
    public OnlyResponseString authEmail(@RequestBody @Valid EmailUserValidRequestDto requestDto){
        emailAuthService.verifyEmail(requestDto.getEmail(), requestDto.getAuthCode());
        return new OnlyResponseString("이메일 인증에 성공했습니다.");
    }


}
