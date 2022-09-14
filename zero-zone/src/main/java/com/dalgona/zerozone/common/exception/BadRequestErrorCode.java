package com.dalgona.zerozone.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BadRequestErrorCode {
    NOT_FOUND("리소스를 찾을 수 없습니다."),
    DUPLICATED("이미 존재하는 리소스입니다."),
    NOT_MATCHES("리소스가 일치하지 않습니다."),
    TIME_OUT("제한 시간이 지났습니다."),
    NOT_AUTHED("인증되지 않은 이메일입니다.");

    private String defaultErrorMessage;
}
