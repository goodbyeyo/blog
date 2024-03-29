package com.blog.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * {
 * "code" : "400",
 * "message" : "잘못된 요청입니다",
 * "validation" : {
 * "title" : "값을 입력해주세요"
 * }
 * }
 */
//@RequiredArgsConstructor
//@Setter
@Getter
//@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class ErrorResponse {

    private final String code;
    private final String message;
    private final Map<String, String> validation;
//    private final Map<String, String> validation = new HashMap<>(); // 초기값이 null 이므로 초기화 필요

    @Builder
    public ErrorResponse(String code, String message, Map<String, String> validation) {
        this.code = code;
        this.message = message;
        this.validation = validation;
    }

    public void addValidation(String field, String errorMessage) {
        this.validation.put(field, errorMessage);
    }

//    @RequiredArgsConstructor
//    private class ValidationTuple {
//        private final String fieldName;
//        private final String errorMessage;
//    }
}
