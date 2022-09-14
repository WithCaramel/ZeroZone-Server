package com.dalgona.zerozone.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class EmailUserValidRequestDto {
    @NotNull(message = "email은 null일 수 없습니다.")
    @Email(message = "email 형식을 지켜야합니다.")
    private String email;

    @NotNull(message = "인증 코드는 빈 값일 수 없습니다.")
    private String authCode;
}
