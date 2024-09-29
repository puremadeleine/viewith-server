package com.puremadeleine.viewith.common;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ApiResponse<T> {

    private T value;

    public static <T> ApiResponse<T> of(T data){
        return ApiResponse.<T>builder()
                .value(data)
                .build();
    }
}
