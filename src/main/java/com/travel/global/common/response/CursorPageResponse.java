package com.travel.global.common.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.function.ToLongFunction;

@Getter
@RequiredArgsConstructor
public class CursorPageResponse<T> {
    private final List<T> data;
    private final Long nextCursor;
    private final Boolean hasNext;

    public static <T> CursorPageResponse<T> of(List<T> data, int pageSize, ToLongFunction<T> idExtractor) {
        boolean hasNext = data.size() > pageSize;
        Long nextCursor = null;

        if (hasNext) {
            T lastItem = data.get(pageSize - 1);
            nextCursor = idExtractor.applyAsLong(lastItem);
            data = data.subList(0, pageSize);
        }

        return new CursorPageResponse<>(data, nextCursor, hasNext);
    }
}