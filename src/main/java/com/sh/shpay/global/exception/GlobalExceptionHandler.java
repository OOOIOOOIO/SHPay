package com.sh.shpay.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.concurrent.TimeoutException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { CustomException.class })
    protected ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
        log.error("handleCustomException throw CustomException : {}", e.getErrorCode());


        return ErrorResponse.toResponseEntity(e.getErrorCode());
    }
    @ExceptionHandler(value = { TimeoutException.class })
    protected ResponseEntity<ErrorResponse> handleTimeException(TimeoutException e) {
        log.error("handleTimeException throw CustomException : {}", e.getMessage());


        return ErrorResponse.toResponseEntity(ErrorCode.FailToAccessBalanceAmount);
    }

}
