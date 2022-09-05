package com.blog.repository;

import com.blog.domain.Post;
import com.blog.domain.QPost;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.blog.domain.QPost.*;


@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public List<Post> getList(int page) {
        return jpaQueryFactory.selectFrom(post)
                .limit(10)
                .offset((long) (page - 1) * 10)
                .orderBy(post.id.desc())
                .fetch();

    }

}

