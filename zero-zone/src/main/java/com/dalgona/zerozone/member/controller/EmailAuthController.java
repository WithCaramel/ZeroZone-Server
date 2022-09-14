package com.dalgona.zerozone.member.controller;

import com.dalgona.zerozone.member.dto.EmailSendResponseDto;
import com.dalgona.zerozone.member.dto.EmailUserAuthRequestDto;
import com.dalgona.zerozone.member.service.EmailAuthService;
import lombok.RequiredArgsConstructor;
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
        emailAuthService.authenticateEmail(requestDto.getEmail());
        return EmailSendResponseDto.of(requestDto.getEmail(), "이메일 전송에 성공했습니다.");
    }

}
