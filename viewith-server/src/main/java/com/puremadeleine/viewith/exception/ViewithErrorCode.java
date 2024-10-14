package com.puremadeleine.viewith.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ViewithErrorCode {

    // venue
    NO_VENUE(HttpStatus.NOT_FOUND, 30001, "no venue"),
    NO_SEAT(HttpStatus.NOT_FOUND, 30002, "no seat"),

    // review
    NO_NORMAL_REVIEW(HttpStatus.NOT_FOUND, 40001, "no normal review"),


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