package com.blog.repository;

import com.blog.domain.Post;

import java.util.List;

public interface PostRepositoryCustom {

    List<Post> getList(int page);
}
