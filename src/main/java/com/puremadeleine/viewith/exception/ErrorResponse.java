package com.puremadeleine.viewith.exception;

import lombok.*;
import org.springframework.http.HttpStatus;

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

    public static ErrorResponse of(HttpStatus httpStatus){
        return ErrorResponse.builder()
                .code(httpStatus.value())
                .message(httpStatus.getReasonPhrase())
                .build();
    }
}
