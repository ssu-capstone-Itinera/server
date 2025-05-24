package com.travel.domain.post.post.dao;

import com.travel.domain.post.post.entity.Post;

import java.util.List;

public interface PostRepositoryCustom {
    List<Post> findRecentPosts(Long cursorId, Integer pageSize);
}