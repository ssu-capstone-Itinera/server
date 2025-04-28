package com.travel.domain.place.service;

import com.travel.domain.llm.dto.TourAttractionReviewDto;
import com.travel.domain.llm.service.GeminiService;
import com.travel.domain.place.dto.TourAttractionDetailDto;
import com.travel.domain.place.dto.TourAttractionLLMDto;
import com.travel.domain.placetype.entity.tourattraction.SubjectiveTag;
import com.travel.global.common.error.CustomException;
import com.travel.global.common.error.ErrorCode;
import org.springframework.stereotype.Service;

import com.travel.domain.place.dto.TourAttractionListDto;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LLMService {
    private final GeminiService geminiService;

    public List<TourAttractionLLMDto> generateTagsWithGemini(List<TourAttractionDetailDto> tourAttractionDetailDtos) {

        List<TourAttractionReviewDto> tourAttractionReviewDtos = extractsReviews(tourAttractionDetailDtos);
        List<List<String>> tagLists = geminiService.extractTourAttractionTags(tourAttractionReviewDtos);

        if (tagLists.size() != tourAttractionReviewDtos.size()) {
            throw new CustomException(ErrorCode.TAG_LIST_SIZE_MISMATCH);
        }
        List<TourAttractionLLMDto> result = getTourAttractionLLMDtos(tourAttractionDetailDtos, tourAttractionReviewDtos, tagLists);

        return result;
    }

    private static List<TourAttractionLLMDto> getTourAttractionLLMDtos(List<TourAttractionDetailDto> tourAttractionDetailDtos, List<TourAttractionReviewDto> tourAttractionReviewDtos, List<List<String>> tagLists) {
        List<TourAttractionLLMDto> result = new ArrayList<>();


        for (int i = 0; i < tourAttractionReviewDtos.size(); i++) {
            TourAttractionReviewDto reviewDto = tourAttractionReviewDtos.get(i);
            List<String> tags = tagLists.get(i);

            // 문자열 태그들을 SubjectiveTag enum으로 바로 변환
            List<SubjectiveTag> subjectiveTagList = tags.stream()
                    .map(tag -> SubjectiveTag.valueOf(tag.trim())) // value 값으로 바로 매핑
                    .toList();


            TourAttractionDetailDto detailDto = tourAttractionDetailDtos.get(i);


            TourAttractionLLMDto tourAttractionLLMDto = TourAttractionLLMDto.builder()
                    .tourAttractionDetailDtoList(List.of(detailDto))
                    .subjectiveTags(subjectiveTagList)
                    .build();

            result.add(tourAttractionLLMDto);
        }
        return result;
    }

    private List<TourAttractionReviewDto> extractsReviews(List<TourAttractionDetailDto> tourAttractionDetailDtos) {
        return tourAttractionDetailDtos.stream()
                .map(detailDto -> TourAttractionReviewDto.builder()
                        .name(detailDto.getName())
                        .placeId(detailDto.getPlaceId())
                        .reviews(detailDto.getReviews())
                        .build())
                .collect(Collectors.toList());
    }
}
