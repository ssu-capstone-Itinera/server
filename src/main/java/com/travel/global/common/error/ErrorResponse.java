package com.travel.global.common.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private final String errorClassName;
    private final String message;

    public static ErrorResponse of(String errorClassName, String message) {
        return new ErrorResponse(errorClassName, message);
    }
}