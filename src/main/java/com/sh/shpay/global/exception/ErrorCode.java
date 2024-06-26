package com.sh.shpay.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // client
    AlreadyExistEmailException(BAD_REQUEST, "C001", "이미 존재하는 이메일입니다."),
    UserNotFoundException(BAD_REQUEST, "C002", "유저가 존재하지 않습니다."),
    DuplicateSaveAttemptedException(BAD_REQUEST, "C003", "중복 저장을 시도하였습니다."),
    DuplicateDeleteAttemptedException(BAD_REQUEST, "C004", "중복 삭제를 시도하였습니다."),
    MethodArgumentNotValidException(BAD_REQUEST, "C005", "유효하지 않은 형식입니다."),
    IllegalArgumentException(BAD_REQUEST,"C006", "적절하지 못한 인자입니다."),
    NoHandlerFoundException(BAD_REQUEST,"C007", "잘못된 uri 요청입니다."),
    MethodNotAllowedException(BAD_REQUEST,"C008", "잘못된 메서드 요청입니다."),


    // server
    NotMatchPasswordException(INTERNAL_SERVER_ERROR, "S001", "비밀번호가 일치하지 않습니다."),
    NotExistUserException(INTERNAL_SERVER_ERROR,"S002", "유저가 존재하지 않습니다."),
    NotExistAccountException(INTERNAL_SERVER_ERROR, "S003", "해당 계좌가 존재하지 않습니다"),
    NotMatchStateException(INTERNAL_SERVER_ERROR, "S004", "state 값이 일치하지 않습니다."),
    NotExistAuthCodeException(INTERNAL_SERVER_ERROR, "S005", "code 값이 존재하지 않습니다."),
    AlreadySaveAccountTypeException(INTERNAL_SERVER_ERROR, "S006", "해당 계좌가 이미 주계좌입니다."),
    FailToAccessBalanceAmount(INTERNAL_SERVER_ERROR, "S007", "계좌조회에 문제가 발생하였습니다. 다시 시도해주세요."),
    NotExistRefreshTokenException(INTERNAL_SERVER_ERROR, "S008", "refresh_token이 존재하지 않습니다. 다시 발급해주세요."),
    NotExistAccessTokenException(INTERNAL_SERVER_ERROR, "S009", "refresh_token 존재하지 않습니다. 다시 발급해주세요."),
    ExpireAccessTokenException(INTERNAL_SERVER_ERROR, "S010", "access_tokend이 만료되었습니다. 다시 발급해주세요."),
    InternalServerException(INTERNAL_SERVER_ERROR, "S011", "서버 에러! 다시 시도해주세요"),


    // security
    UsernameNotFoundException(NOT_FOUND, "SC001", "유저가 존재하지 않습니다."),
    UnauthorizedException(UNAUTHORIZED, "SC002", "인증에 실패하였습니다.");



    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
