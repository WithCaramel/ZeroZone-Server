package com.dalgona.zerozone.member.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@RequiredArgsConstructor
public class MemberLoginRequestDto {
    @NotNull(message = "email은 null일 수 없습니다.")
    @Email(message = "email 형식을 지켜야합니다.")
    private String email;

    @NotEmpty(message = "회원의 password는 빈값일 수 없습니다.")
    private String password;
}
