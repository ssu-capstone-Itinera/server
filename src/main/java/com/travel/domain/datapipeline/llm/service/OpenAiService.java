package com.travel.domain.datapipeline.llm.service;

import com.travel.domain.datapipeline.llm.dto.TourAttractionReviewDto;
import com.travel.domain.datapipeline.llm.entity.Prompt;
import com.travel.global.common.error.CustomException;
import com.travel.global.common.error.ErrorCode;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OpenAiService {

    @Value("${ai.openai.api-key}")
    private String apiKey;

    @Value("${ai.openai.model}")
    private String model;

    @Value("${ai.openai.api-url}")
    private String apiUrl;

    private WebClient webClient;

    @PostConstruct
    public void initWebClient() {
        this.webClient = WebClient.builder()
                .baseUrl(apiUrl)
                .defaultHeader("Authorization", "Bearer " + apiKey)
                .defaultHeader("Content-Type", "application/json")
                .build();
    }

    public List<List<String>> extractTourAttractionTags(List<TourAttractionReviewDto> tourAttractionReviewDtos) {
        List<List<String>> allTags = new ArrayList<>();

        for (int i = 0; i < tourAttractionReviewDtos.size(); i += 10) {
            int end = Math.min(i + 10, tourAttractionReviewDtos.size());
            List<TourAttractionReviewDto> subList = tourAttractionReviewDtos.subList(i, end);
            Prompt prompt = Prompt.builder().reviews(subList).build();
            Map<String, Object> openAiRequest = convertPromptToOpenAIRequest(prompt);

            log.info("OpenAI Request [{} ~ {}]: {}", i, end - 1, openAiRequest);

            List<List<String>> subTags = extractTags(openAiRequest);
            allTags.addAll(subTags);
        }

        return allTags;
    }


    public List<List<String>> extractTags(Map<String, Object> request) {
        Mono<String> responseMono = webClient
                .post()
                .uri("/chat/completions")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(Map.class)
                .map(responseMap -> {
                    List<Map<String, Object>> choices = (List<Map<String, Object>>) responseMap.get("choices");
                    if (choices == null || choices.isEmpty()) return "";
                    Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
                    return message != null ? (String) message.get("content") : "";
                });

        String resultText = responseMono.block();

        if (resultText == null || resultText.isEmpty()) {
            throw new CustomException(ErrorCode.GOOGLE_API_CALL_FAILED);
        }

        log.info("Result from OpenAI: {}", resultText);
        return parseFixedFormatResult(resultText);
    }

    private List<List<String>> parseFixedFormatResult(String resultText) {
        List<List<String>> tagLists = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\((.*?)\\)");
        Matcher matcher = pattern.matcher(resultText);

        while (matcher.find()) {
            String group = matcher.group(1);
            List<String> tags = Arrays.stream(group.split(","))
                    .map(String::trim)
                    .map(tag -> tag.replaceAll("\"", "").replaceAll("'", ""))
                    .filter(tag -> !tag.isEmpty())
                    .collect(Collectors.toList());
            tagLists.add(tags);
        }

        return tagLists;
    }

    public Map<String, Object> convertPromptToOpenAIRequest(Prompt prompt) {
        Map<String, Object> request = new HashMap<>();
        List<Map<String, String>> messages = new ArrayList<>();

        Map<String, String> systemMessage = new HashMap<>();
        systemMessage.put("role", "system");
        systemMessage.put("content", "You are an AI assistant that analyzes tourist attraction reviews and extracts short, relevant emotional and thematic tags for each place. Return tags in parentheses like (calm, relaxing, scenic).");
        messages.add(systemMessage);

        StringBuilder userContent = new StringBuilder();
        userContent.append(Prompt.DEFAULT_QUESTION + "\n");

        for (TourAttractionReviewDto reviewDto : prompt.getReviews()) {
            userContent.append("placeName: ").append(reviewDto.getName()).append("\n");
            userContent.append("Reviews:\n");
            for (String review : reviewDto.getReviews()) {
                userContent.append("- ").append(review).append("\n");
            }
            userContent.append("\n");
        }

        Map<String, String> userMessage = new HashMap<>();
        userMessage.put("role", "user");
        userMessage.put("content", userContent.toString());
        messages.add(userMessage);

        request.put("model", model);
        request.put("messages", messages);

        log.info("Request to OpenAI: {}", request);
        return request;
    }
}
