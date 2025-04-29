package com.travel.domain.datapipeline.llm.dto;

import lombok.Data;
import java.util.List;

@Data
public class GeminiRequest {

    private List<Content> contents;

    @Data
    public static class Content {
        private List<Part> parts;
    }

    @Data
    public static class Part {
        private String text;
    }
}
