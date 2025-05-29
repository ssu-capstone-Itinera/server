package com.travel.domain.placetype.dao.tourattraction;

import java.util.List;

import com.travel.domain.placetype.entity.tourattraction.SubjectiveTag;
import com.travel.domain.placetype.entity.tourattraction.TourattractionDoc;
import com.travel.domain.placetype.entity.tourattraction.TourattractionTag;

public interface TourattractionRepositoryCustom {
    List<TourattractionDoc> findPlaceGoogleIdsByAddressAndTourattractionTags(
            String address, TourattractionTag tourattractionTag);

    List<TourattractionDoc> findPlaceGoogleIdsByAddressAndSubjectiveTags(
            String address, SubjectiveTag subjectiveTag);
}
