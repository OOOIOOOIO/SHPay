package com.sh.shpay.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    NOT_FOUND_MENU(BAD_REQUEST, "존재하지 않는 메뉴입니다."),
    NOT_FOUND_MENU_LIST(BAD_REQUEST, "존재하지 않는 메뉴입니다."),
    NOT_FOUND_USER(BAD_REQUEST, "회원 정보가 올바르지 않습니다."),
    ALREADY_EXIST_EMAIL(BAD_REQUEST, "이미 존재하는 이메일입니다."),
    S3_FILE_UPLOAD(BAD_REQUEST, "S3 file upload에 실패하였습니다."),
    FAIL_SAVE_IN_REDIS(BAD_REQUEST, "redis에 데이터 저장에 실패하였습니다."),
    FAIL_DELETE_FROM_REDIS(BAD_REQUEST, "redis에서 데이터 삭제에 실패하였습니다."),
    ALREADY_SAVE(BAD_REQUEST, "중복 저장을 시도하였습니다."),
    ALREADY_DELETE(BAD_REQUEST, "중복 삭제를 시도하였습니다.");


    private final HttpStatus httpStatus;
    private final String message;
}
