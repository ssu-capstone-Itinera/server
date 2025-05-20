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
    TRIP_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 여행 일정입니다."),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 게시글입니다."),
    POSTLIKE_NOT_FOUND(HttpStatus.NOT_FOUND,"존재하지 않는 좋아요입니다."),
    POSTCOMMENT_NOT_FOUNT(HttpStatus.NOT_FOUND, "존재하지 않는 댓글입니다."),


            //Gemini
    GEMINI_API_CALL_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "Gemini API 호출에 실패하였습니다."),
    GEMINI_EMPTY_RESPONSE(HttpStatus.INTERNAL_SERVER_ERROR, "Gemini 응답이 비어 있습니다."),
    TAG_LIST_SIZE_MISMATCH(HttpStatus.INTERNAL_SERVER_ERROR, "요청 장소 수와 태그 리스트 수가 일치하지 않습니다."),

    FOLLOW_MYSELF_FAILED(HttpStatus.BAD_REQUEST, "본인 계정을 팔로우 할 수 없습니다."),
    ALREADY_FOLLOWED(HttpStatus.BAD_REQUEST, "이미 팔로우한 계정입니다."),
    PLACE_NOT_FOUND(HttpStatus.NOT_FOUND, "장소 정보를 찾을 수 없습니다."),
    RESTAURANTDOC_NOT_FOUND(HttpStatus.NOT_FOUND, "식당 정보를 찾을 수 없습니다."),
    CAFEDOC_NOT_FOUND(HttpStatus.NOT_FOUND, "카 정보를 찾을 수 없습니다."),
    TOURATTRACTIONDOC_NOT_FOUND(HttpStatus.NOT_FOUND, "관광지 정보를 찾을 수 없습니다.");


    private final HttpStatus status;
    private final String message;
}
