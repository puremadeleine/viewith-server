package com.puremadeleine.viewith.exception;

import lombok.Getter;

@Getter
public class ViewithException extends RuntimeException {

    ViewithErrorCode errorCode;

    public ViewithException(ViewithErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ViewithException(ViewithErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}