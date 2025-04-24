package com.travel.domain.placetype.entity.lodge;

import org.springframework.data.elasticsearch.annotations.Document;

import com.travel.domain.place.entity.PlaceDocument;

@Document(indexName = "lodges")
public class LodgeDoc extends PlaceDocument {}
