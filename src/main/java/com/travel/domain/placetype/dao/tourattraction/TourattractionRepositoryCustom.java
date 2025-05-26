package com.travel.domain.placetype.dao.tourattraction;

import java.util.List;

import com.travel.domain.placetype.entity.tourattraction.SubjectiveTag;
import com.travel.domain.placetype.entity.tourattraction.TourattractionTag;

public interface TourattractionRepositoryCustom {
    List<String> findPlaceGoogleIdsByAddressAndTourattractionTags(
            String address, TourattractionTag tourattractionTag);

    List<String> findPlaceGoogleIdsByAddressAndSubjectiveTags(
            String address, SubjectiveTag subjectiveTag);
}
