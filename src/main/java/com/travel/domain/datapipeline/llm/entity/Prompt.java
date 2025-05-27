package com.travel.domain.datapipeline.llm.entity;

import java.util.List;

import com.travel.domain.datapipeline.llm.dto.TourAttractionReviewDto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Prompt {

    public static final String DEFAULT_QUESTION =
            "아래는 장소 리뷰입니다. 각 장소(placeName + 리뷰들)는 {} 안에 있습니다. 각 장소에 해당하는 태그들을 리스트형태로 넣어주세요.\n"
                    + "\n"
                    + "당신은 다음 규칙을 반드시 따라야 합니다. :\n"
                    + "\n"
                    + "1. 장소 개수(=placeName 수)와 결과로 반환되는 괄호의 총 개수(=() 개수)는 **반드시 개수가 일치**해야 합니다. \n"
                    + "2. 각 장소에 대해 적합한 태그들을 괄호 () 안에 쉼표로 구분해 나열하세요.\n"
                    + "3. 한 장소에 태그가 하나도 없으면 빈 괄호 ()로 표시합니다.\n"
                    + "4. 장소 순서와 괄호 순서는 반드시 동일해야 합니다.\n"
                    + "5. 출력은 반드시 하나의 리스트 형태 [(), (), ...] 로만 하세요. 다른 설명, 텍스트는 절대 추가하지 마세요.\n"
                    + "6. 이 규칙을 지키지 않으면 답변은 무효입니다. 태그를 생성후 개수 조건을 다시 확인하세 ㄸ\n"
                    + "\n"
                    + "사용할 수 있는 태그 목록: [\"활발한\", \"한적한\", \"힐링\",  \"인스타감성 \", \"낭만적인\", \"여유로운\", \"이국적인\", \"전통적인\"]\n"
                    + "\n"
                    + "예시: [(\"활발한\", \"낭만적인\"), (), (\"낭만적인\"), ...]\n"
                    + "\n"
                    + "아래 장소와 리뷰를 기반으로 결과를 생성하세요:\n";
    private List<TourAttractionReviewDto> reviews;

}
