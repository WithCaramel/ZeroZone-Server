package com.dalgona.zerozone.security.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class TokenRequestDto {
    @NotNull(message = "accessToken는 null일 수 없습니다.")
    @NotEmpty(message = "accessToken는 빈값일 수 없습니다.")
    String accessToken;

    @NotNull(message = "refreshToken는 null일 수 없습니다.")
    @NotEmpty(message = "refreshToken는 빈값일 수 없습니다.")
    String refreshToken;

    @Builder
    public TokenRequestDto(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
