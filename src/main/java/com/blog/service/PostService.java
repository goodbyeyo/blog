package com.blog.service;

import com.blog.domain.Post;
import com.blog.repository.PostRepository;
import com.blog.request.PostCreate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor    //
public class PostService {
    private final PostRepository repository; // final 로 선언된 필드는 @RequiredArgsConstructor 로 생성자 생성 가능


    // public Post write(PostCreate postCreate) {
    // public Long write(PostCreate postCreate) {
    public void write(PostCreate postCreate) {
        // postCreate -> Entity 변한 필요
        // 직접 필드 주입 : 비추천 (변경사항은 외부에 닫는 방법으로)
        // Post post = new Post();
        // post.title = postCreate.getTitle();
        // post.content = postCreate.getContent();

        // Post post = new Post(postCreate.getTitle(), postCreate.getContent());
        Post post = Post.builder()
                .title(postCreate.getTitle())
                .content(postCreate.getContent())
                .build();
        // return repository.save(post);
        // return post.getId();
        repository.save(post);
    }
}
