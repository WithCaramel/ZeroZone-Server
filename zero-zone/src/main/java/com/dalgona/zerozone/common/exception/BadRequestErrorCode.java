package com.dalgona.zerozone.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BadRequestErrorCode {
    NOT_FOUND("리소스를 찾을 수 없습니다."),
    DUPLICATED("이미 존재하는 리소스입니다.");

    private String defaultErrorMessage;
}
