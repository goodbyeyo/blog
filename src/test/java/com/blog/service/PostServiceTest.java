package com.blog.service;

import com.blog.domain.Post;
import com.blog.repository.PostRepository;
import com.blog.request.PostCreate;
import com.blog.request.PostSearch;
import com.blog.response.PostResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.data.domain.Sort.Direction.DESC;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository repository;

    @BeforeEach
    void clean() {
        repository.deleteAll();
    }

    @Test
    @DisplayName("글 작성")
    void test1() {
        // given
        PostCreate postCreate = PostCreate.builder()
                .title("제목입니다")
                .content("내용입니다")
                .build();

        // when
        postService.write(postCreate);

        // then
        assertEquals(1L, repository.count());
        Post post = repository.findAll().get(0);
        assertEquals("제목입니다",post.getTitle());
        assertEquals("내용입니다",post.getContent());
    }

    @Test
    @DisplayName("글 1개 조회")
    void test2() {
        // given
        Post request = Post.builder()
                .title("foo")
                .content("bar")
                .build();
        repository.save(request);

        // when
        PostResponse response = postService.get(request.getId());

        // then
        assertNotNull(response);
        assertEquals("foo", response.getTitle());
        assertEquals("bar", response.getContent());
    }

    @Test
    @DisplayName("글 다건 조회")
    void test3() {
        // given
        Post request1 = Post.builder()
                .title("foo1")
                .content("bar1")
                .build();
        repository.save(request1);

        Post request2 = Post.builder()
                .title("foo2")
                .content("bar2")
                .build();
        repository.save(request2);

        // when
        // Pageable pageable = PageRequest.of(0, 5, DESC, "id");
        PostSearch search = PostSearch.builder()
                .page(1)
                .size(10)
                .build();
        List<PostResponse> postList = postService.getList(search);

        // then
        assertNotNull(postList);
        assertEquals(2L, postList.size());
    }

    @Test
    @DisplayName("페이징 1페이지 조회")
    void paging() {
        // given
        List<Post> requestList = IntStream.range(1, 31) // for (int i = 0; i < 30; i++) {
                .mapToObj(i-> Post.builder()
                            .title("title " + i)
                            .content("content " + i)
                            .build())
                .collect(Collectors.toList());
        repository.saveAll(requestList);

        // Pageable pageable = PageRequest.of(0, 5, DESC, "id");
        PostSearch search = PostSearch.builder()
                .page(1)
                .size(10)
                .build();
        // when
        List<PostResponse> postList = postService.getList(search);

        // then
        assertNotNull(postList);
        assertEquals(10L, postList.size());
        assertEquals("title 30", postList.get(0).getTitle()); // order by desc
//        assertEquals("title 30", postList.get(0).getTitle());
//        assertEquals("title 26", postList.get(4).getTitle());
    }
}