package com.puremadeleine.viewith.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ViewithErrorCode {

    // common
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, 10000, "invalid token"),
    FORBIDDEN_TOKEN(HttpStatus.FORBIDDEN, 10001, "forbidden token"),
    INVALID_PARAM(HttpStatus.BAD_REQUEST, 10002, "invalid param"),

    // member
    DUPLICATED_NICKNAME(HttpStatus.CONFLICT, 20001, "duplicated nickname"),
    INVALID_NICKNAME_FORMAT(HttpStatus.BAD_REQUEST, 20002, "invalid nickname format"),

    // venue
    NO_VENUE(HttpStatus.NOT_FOUND, 30001, "no venue"),
    NO_SEAT(HttpStatus.NOT_FOUND, 30002, "no seat"),

    // review
    NO_NORMAL_REVIEW(HttpStatus.NOT_FOUND, 40001, "no normal review"),
    PERMISSION_DENIED_FOR_REVIEW(HttpStatus.NOT_FOUND, 40002, "permission denied for this review"),

    // help
    NO_HELP(HttpStatus.NOT_FOUND, 50001, "no help"),

    // image
    IMAGE_UPLOAD_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, 60001, "image upload failed"),

    UNKNOWN_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR, 99999, "unknown exception occured");

    private final HttpStatus httpStatus;
    private final Integer code;
    private final String message;

    ViewithErrorCode(HttpStatus httpStatus, Integer code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}