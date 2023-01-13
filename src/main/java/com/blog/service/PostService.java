package com.blog.service;

import com.blog.domain.Post;
import com.blog.domain.PostEditor;
import com.blog.exception.PostNotFound;
import com.blog.repository.PostRepository;
import com.blog.request.PostCreate;
import com.blog.request.PostEdit;
import com.blog.request.PostSearch;
import com.blog.response.PostResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public PostResponse get(Long id) {
        Post post = repository.findById(id)
                .orElseThrow(PostNotFound::new);
                // .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다."));
        PostResponse response = PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .build();
        return response;
//        Optional<Post> postOptional = repository.findById(id);
//        if (postOptional.isPresent()) {
//            return postOptional.get();
//        }

    }

//    public List<PostResponse> getList(int page) {
//    public List<PostResponse> getList(Pageable pageable) {
    public List<PostResponse> getList(PostSearch postSearch) {
        // web 에서 page 1 요청 -> 0으로 변경함
//        Pageable pageable = PageRequest.of(page, 5, Sort.by(Sort.Direction.DESC,"id"));
        return repository.getList(postSearch).stream()
                // 생성자 오버로딩을 통해서 축약 가능
                .map(PostResponse::new)
                .collect(Collectors.toList());
//        return repository.findAll().stream()
//                .map(post -> PostResponse.builder()
//                        .id(post.getId())
//                        .title(post.getTitle())
//                        .content(post.getContent())
//                        .build())
//                .collect(Collectors.toList());
    }

    @Transactional
    public PostResponse edit(Long id, PostEdit postEdit) {
        Post post = repository.findById(id)
                .orElseThrow(PostNotFound::new);

        // 1 ( Builer 패턴 이용해서 전체 저장하여 저장하는 방법 )
        PostEditor.PostEditorBuilder postEditorBuilder = post.toEditor();
        PostEditor postEditor = postEditorBuilder
                .title(postEdit.getTitle())
                .content(postEdit.getContent())
                .build();
        post.edit(postEditor);
        return new PostResponse(post);

        // 2 ( 변경되어 전달된 내용만 null 체크하여 저장하는 방법) - 생성자안에서 null 체크할수도 있다
//        if (postEdit.getTitle() != null) {
//            postEditorBuilder.title(postEdit.getTitle());
//        }
//        if (postEdit.getContent() != null) {
//            postEditorBuilder.content(postEdit.getContent());
//        }
//        post.edit(postEditorBuilder.build());

        // 3 메서드 이용해서 setter 로 바로 주입하는 방법
        // post.change(postEdit.getTitle(), postEdit.getContent());
    }

    public void delete(Long id) {
        Post post = repository.findById(id)
                .orElseThrow(PostNotFound::new);
        repository.delete(post);
    }
}
