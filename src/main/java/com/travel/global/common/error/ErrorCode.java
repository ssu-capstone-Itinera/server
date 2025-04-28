package com.travel.global.common.error;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    SAMPLE_ERROR(HttpStatus.BAD_REQUEST, "Sample Error Message"),
    AUTH_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR, "시큐리티 인증 정보를 찾을 수 없습니다."),
    KAKAO_USER_INFO_FAILED(HttpStatus.BAD_REQUEST, "카카오 유저 정보 조회를 실패하였습니다."),

    GOOGLE_API_CALL_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "구글 API 호출에 실패하였습니다."),

    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 유저입니다."),


    //Gemini
    GEMINI_API_CALL_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "Gemini API 호출에 실패하였습니다."),
    GEMINI_EMPTY_RESPONSE(HttpStatus.INTERNAL_SERVER_ERROR, "Gemini 응답이 비어 있습니다."),
    TAG_LIST_SIZE_MISMATCH(HttpStatus.INTERNAL_SERVER_ERROR, "요청 장소 수와 태그 리스트 수가 일치하지 않습니다.")

    ;

    private final HttpStatus status;
    private final String message;
}
