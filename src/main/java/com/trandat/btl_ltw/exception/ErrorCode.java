package com.trandat.btl_ltw.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    UNCATEGORRIZED_EXCEPTION(999, "uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1001, "invalid message key", HttpStatus.BAD_REQUEST),
    USER_EXISTED(1002, "User existed",  HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1005,"User not existed", HttpStatus.NOT_FOUND),
    USERNAME_INVALID(1003, "username must be at least 3 character",  HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(1004, "password must be at least 8 character",  HttpStatus.BAD_REQUEST),
    UNAUTHENTICATED(1006, "unauthenticated",  HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, "You do not have permission", HttpStatus.FORBIDDEN),
    KYTHI_EXISTED(1008, "KyThi existed",  HttpStatus.BAD_REQUEST),
    KYTHI_NOT_EXISTED(1009,"kythi not existed", HttpStatus.NOT_FOUND),
    MONTHI_EXISTED(1010, "Monthi existed",  HttpStatus.BAD_REQUEST),
    MONTHI_NOT_EXISTED(1011,"Monthi not existed", HttpStatus.NOT_FOUND),
    CAUHOI_EXISTED(1012, "CauHoi existed",  HttpStatus.BAD_REQUEST),
    CAUHOI_NOT_EXISTED(1011,"CauHoi not existed", HttpStatus.NOT_FOUND),
    DETHI_EXISTED(1012, "dethi existed",  HttpStatus.BAD_REQUEST),
    DETHI_NOT_EXISTED(1011,"dethi not existed", HttpStatus.NOT_FOUND),
    ;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private int code;
    private String message;
    private HttpStatusCode statusCode;
}
