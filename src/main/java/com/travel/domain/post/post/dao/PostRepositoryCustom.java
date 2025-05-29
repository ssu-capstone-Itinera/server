package com.travel.domain.post.post.dao;

import java.util.List;

import com.travel.domain.post.post.entity.Post;

public interface PostRepositoryCustom {
    List<Post> findRecentPosts(Long cursorId, Integer pageSize);
}
