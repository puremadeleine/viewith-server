package com.puremadeleine.viewith.exception;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ErrorResponse {

    private Integer code;
    private String message;

    public static ErrorResponse of(ViewithErrorCode errorCode){
        return ErrorResponse.builder()
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();
    }

    public static ErrorResponse of(ViewithErrorCode errorCode, String detailMessage){
        return ErrorResponse.builder()
                .code(errorCode.getCode())
                .message(detailMessage != null ? detailMessage : errorCode.getMessage())
                .build();
    }
}
