package com.puremadeleine.viewith.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    private T value;

    public static ApiResponse of(){
        return ApiResponse.builder()
                .build();
    }

    public static <T> ApiResponse<T> of(T data){
        return ApiResponse.<T>builder()
                .value(data)
                .build();
    }
}
