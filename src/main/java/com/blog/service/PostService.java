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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository repository;

    public void write(PostCreate postCreate) {

        Post post = Post.builder()
                .title(postCreate.getTitle())
                .content(postCreate.getContent())
                .build();
        repository.save(post);
    }

    public PostResponse get(Long id) {
        Post post = repository
                .findById(id)
                .orElseThrow(PostNotFound::new);
        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .build();
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


//    public PostResponse edit(Long id, PostEdit postEdit) {
    @Transactional
    public void edit(Long id, PostEdit postEdit) {

        Post post = repository
                .findById(id)
                .orElseThrow(PostNotFound::new);

        PostEditor.PostEditorBuilder editorBuilder = post.toEditor();

        PostEditor postEditor = editorBuilder
                .title(postEdit.getTitle())
                .content(postEdit.getContent())
                .build();

        post.edit(postEditor);


//        post.edit(
//                postEdit.getTitle() != null ? postEdit.getTitle() : post.getTitle(),
//                postEdit.getContent() != null ? postEdit.getContent() : post.getContent()
//        );

//        return new PostResponse(post);

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
