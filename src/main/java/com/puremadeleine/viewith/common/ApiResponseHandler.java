package com.puremadeleine.viewith.common;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import static java.util.Objects.isNull;

@RestControllerAdvice
public class ApiResponseHandler implements ResponseBodyAdvice {

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        // 요청의 HTTP 메서드에 따라 상태 코드 설정
        HttpMethod method = request.getMethod();
        if (method == HttpMethod.POST) {
            response.setStatusCode(HttpStatus.CREATED);
        } else if (method == HttpMethod.PUT && isNull(body)) {
            response.setStatusCode(HttpStatus.NO_CONTENT);
        }
        return body;
    }
}
