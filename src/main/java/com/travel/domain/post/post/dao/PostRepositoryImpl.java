package com.travel.domain.post.post.dao;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.travel.domain.post.post.entity.Post;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.travel.domain.post.post.entity.QPost.post;

@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    /**
     * 최신 등록 순으로 게시글을 조회 (커서 기반 페이지네이션)
     */
    @Override
    public List<Post> findRecentPosts(Long cursorId, Integer pageSize) {
        return queryFactory
                .selectFrom(post)
                .where(cursorId != null ? post.id.lt(cursorId) : null)
                .orderBy(post.id.desc())
                .limit(pageSize)
                .fetch();
    }
}