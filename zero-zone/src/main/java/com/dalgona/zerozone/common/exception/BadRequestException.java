package com.dalgona.zerozone.common.exception;

import lombok.Getter;

@Getter
public class BadRequestException extends RuntimeException {
    private BadRequestErrorCode errorCode;
    public String errorMessage;

    public BadRequestException(BadRequestErrorCode errorCode){
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getDefaultErrorMessage();
    }
    public BadRequestException(BadRequestErrorCode errorCode, String errorMessage){
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
