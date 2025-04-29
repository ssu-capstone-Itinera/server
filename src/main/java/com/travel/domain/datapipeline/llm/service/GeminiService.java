package com.travel.domain.datapipeline.llm.service;

import com.travel.domain.datapipeline.llm.dto.GeminiRequest;
import com.travel.domain.datapipeline.llm.dto.GeminiResponse;
import com.travel.domain.datapipeline.llm.dto.TourAttractionReviewDto;
import com.travel.domain.datapipeline.llm.entity.Prompt;
import com.travel.global.common.error.CustomException;
import com.travel.global.common.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class GeminiService {

    @Value("${ai.gemini.api-key}")
    private String apiKey;

    @Value("${ai.gemini.model}")
    private String model;

    @Value("${ai.gemini.api-url}")
    private String apiUrl;


    private WebClient webClient = WebClient.create();

    public List<List<String>> extractTourAttractionTags(List<TourAttractionReviewDto> tourAttractionReviewDtos) {
        Prompt prompt = Prompt.builder()
                .reviews(tourAttractionReviewDtos)
                .build();

        GeminiRequest request = convertPromptToGeminiRequest(prompt);

        log.info(request.toString());

        return extractTags(request);
    }

    public List<List<String>> extractTags(GeminiRequest request) {
        // Gemini API에 요청 본문으로 GeminiRequest 객체를 전달
        GeminiResponse response = webClient.post()
                .uri(apiUrl + model + ":generateContent?key=" + apiKey)
                .header("Content-Type", "application/json")
                .bodyValue(request) // GeminiRequest 객체를 본문으로 전달
                .retrieve()
                .bodyToMono(GeminiResponse.class)
                .block();


        if (response == null || response.getCandidates().isEmpty()) {
            throw new CustomException(ErrorCode.GOOGLE_API_CALL_FAILED);
        }

        String resultText = response.getCandidates().get(0).getContent().getParts().get(0).getText();


        //log.info(resultText);

        return parseFixedFormatResult(resultText);
    }

    private List<List<String>> parseFixedFormatResult(String resultText) {
        List<List<String>> tagLists = new ArrayList<>();

        //( ) 안의 내용 찾기
        Pattern pattern = Pattern.compile("\\((.*?)\\)");
        Matcher matcher = pattern.matcher(resultText);

        while (matcher.find()) {
            String group = matcher.group(1);
            List<String> tags = List.of(group.split(","))
                    .stream()
                    .map(String::trim)
                    .map(tag -> tag.replaceAll("\"", "").replaceAll("'", "")) // 따옴표 제거
                    .filter(tag -> !tag.isEmpty())
                    .collect(Collectors.toList());
            tagLists.add(tags);
        }

        return tagLists;
    }

    // Prompt 객체를 GeminiRequest 객체로 변환하는 메서드
    private GeminiRequest convertPromptToGeminiRequest(Prompt prompt) {
        GeminiRequest request = new GeminiRequest();
        GeminiRequest.Content content = new GeminiRequest.Content();
        List<GeminiRequest.Part> parts = new ArrayList<>();

        StringBuilder question = new StringBuilder();
        question.append(Prompt.DEFAULT_QUESTION);
        question.append("\n장소와 리뷰 목록은 다음과 같습니다:\n[");

        // 각 장소별로 {placeName: ..., reviews: [...]} 형태로 추가
        for (TourAttractionReviewDto reviewDto : prompt.getReviews()) {
            question.append("{");
            question.append("\"placeName\": \"").append(reviewDto.getName()).append("\", ");
            question.append("\"reviews\": [");

            // 리뷰 여러 개라면 리스트로 처리
            List<String> reviews = reviewDto.getReviews(); // List<String> 타입이라고 가정
            for (int i = 0; i < reviews.size(); i++) {
                question.append("\"").append(reviews.get(i)).append("\"");
                if (i < reviews.size() - 1) {
                    question.append(", ");
                }
            }

            question.append("]");
            question.append("}, ");
        }

        // 마지막 쉼표 제거
        if (prompt.getReviews().size() > 0) {
            question.setLength(question.length() - 2);
        }
        question.append("]");

        GeminiRequest.Part part = new GeminiRequest.Part();
        part.setText(question.toString());
        parts.add(part);

        content.setParts(parts);
        request.setContents(List.of(content));

        return request;
    }

}
