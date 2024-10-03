package com.puremadeleine.viewith.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ViewithErrorCode {

    NO_VENUE(HttpStatus.NOT_FOUND, 30001, "no venue content"),
    UNKNOWN_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR , 99999, "unknown exception occured");

    private final HttpStatus httpStatus;
    private final Integer code;
    private final String message;

    ViewithErrorCode(HttpStatus httpStatus, Integer code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}