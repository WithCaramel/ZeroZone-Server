package com.dalgona.zerozone.common.exception;

import com.dalgona.zerozone.common.response.ErrorEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class CommonExceptionAdvice {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorEntity handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error("Http Message Not Readable Exception");
        return new ErrorEntity("HttpMessageNotReadableException", "Required request body is missing");
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorEntity handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        log.error("Missing Servlet Request Parameter Exception");
        return new ErrorEntity("MissingServletRequestParameterException", e.getParameterName() + " is missing");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorEntity handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String errorCode = e.getBindingResult().getAllErrors().get(0).getCode();
        String errorMessage = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        log.error("MethodArgumentNotValid Exception({}) - {}", errorCode, errorMessage);
        return new ErrorEntity(errorCode, errorMessage);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorEntity handleAllExceptions(Exception ex) {
        Map<String, String> error = new HashMap<>();
        return new ErrorEntity("Exception", ex.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorEntity handleValidationExceptions(BadRequestException e) {
        log.error("Bad Request Exception({}) - {}", e.getErrorCode(), e.getErrorMessage());
        return new ErrorEntity(e.getErrorCode().toString(), e.getErrorMessage());
    }

    @ExceptionHandler(InternalServerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorEntity commonJpaException(InternalServerException e) {
        log.error("Internal Server Exception({}) - {}", e.getErrorCode(), e.getErrorMessage());
        return new ErrorEntity(e.getErrorCode().toString(), e.getErrorMessage());
    }

}
