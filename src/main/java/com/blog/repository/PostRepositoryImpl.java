package com.blog.repository;

import com.blog.domain.Post;
import com.blog.domain.PostDto;
import com.blog.domain.QPost;
import com.blog.domain.QPostDto;
import com.blog.request.PostSearch;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.blog.domain.QPost.*;


@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public List<Post> getList(PostSearch postSearch) {
        return jpaQueryFactory.selectFrom(post)
                .limit(postSearch.getSize())
                .offset(postSearch.getOffset())
                // .offset((long)(postSearch.getPage() - 1) * postSearch.getSize())
                .orderBy(post.id.desc())
                .fetch();

    }

    @Override
    public List<PostDto> selectEditorContent(Post post) {
        return null;
//        return jpaQueryFactory
//                .select(QPostDto.),
//                        ExpressionUtils.as(
//                                JPAExpressions
//                                        .select(QPostEditor.)
//
//                        .)
    }

}

