package com.blog.repository;

import com.blog.domain.Post;
import com.blog.domain.PostDto;
import com.blog.request.PostSearch;

import java.util.List;

public interface PostRepositoryCustom {

    List<Post> getList(PostSearch postSearch);

    List<PostDto> selectEditorContent(Post post);
}
