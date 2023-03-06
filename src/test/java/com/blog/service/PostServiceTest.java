package com.blog.service;

import com.blog.domain.Post;
import com.blog.domain.PostDto;
import com.blog.domain.PostEditor;
import com.blog.exception.PostNotFound;
//import com.blog.repository.PostEditorRepository;
import com.blog.repository.PostRepository;
import com.blog.request.PostCreate;
import com.blog.request.PostEdit;
import com.blog.request.PostSearch;
import com.blog.response.PostResponse;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository repository;

    // @Autowired
    // private PostEditorRepository postEditorRepository;

    @BeforeEach
    void clean() {
        repository.deleteAll();
    }

//    @Test
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

//    @Test
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

//    @Test
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

//    @Test
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

    @Test
    @DisplayName("글 수정 null 값 입력 테스트")
    void modifyNormalTest() {
        // given (기본 데이터 입력)
        Post post = Post.builder()
                .title("title")
                .content("content")
                .build();
        repository.save(post);

        // when (수정할 데이터 입력)
        PostEdit postEdit = PostEdit.builder()
                .title("title1")
                .content("content1")
                .build();

        postService.edit(post.getId(), postEdit);

        // then
        Post changedResult = repository.findById(post.getId())
                .orElseThrow(() -> new RuntimeException("글이 존재하지 않습니다 id = " + post.getId()));

        assertEquals("title1", changedResult.getTitle());
        assertEquals("content1", changedResult.getContent());
    }

    @Test
    @DisplayName("글 수정 null 값 입력 테스트")
    void modifyNullTest() {
        // given (기본 데이터 입력)
        Post post = Post.builder()
                .title("title")
                .content("content")
                .build();
        repository.save(post);

        // when (수정할 데이터 입력)
        PostEdit postEdit = PostEdit.builder()
                .title(null)
                .content("content1")
                .build();

        postService.edit(post.getId(), postEdit);

        // then
        Post changedResult = repository.findById(post.getId())
                .orElseThrow(() -> new RuntimeException("글이 존재하지 않습니다 id = " + post.getId()));

        assertEquals("title", changedResult.getTitle());
        assertEquals("content1", changedResult.getContent());
    }

//    @Test
    @DisplayName("글 삭제 테스트")
    void deleteTest() {
        // given (기본 데이터 입력)
        Post post = Post.builder()
                .title("title")
                .content("content")
                .build();
        repository.save(post);

        // when
        postService.delete(post.getId());

        // then
        Assertions.assertEquals(0, repository.count());
    }

//    @Test
    @DisplayName("글 1개 조회 실패 케이스 테스트")
    void selectFailTest() {
        // given
        Post post = Post.builder()
                .title("foo")
                .content("bar")
                .build();
        repository.save(post);

        // post.getId() // primary_id = 1

        // expected
        assertThrows(PostNotFound.class, () -> {
            postService.get(post.getId() + 1L);
        });
//        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
//            postService.get(post.getId() + 1L);
//        });
//        Assertions.assertEquals("존재하지 않는 글입니다.", e.getMessage());

//        Assertions.assertThrows(NullPointerException.class, () -> {
//            postService.get(post.getId() + 1L);
//        }, "예외처리가 잘못 되었습니다");

        // PostResponse response = postService.get(post.getId()+1L);

        // then
//        assertNotNull(response);
//        assertEquals("foo", response.getTitle());
//        assertEquals("bar", response.getContent());
    }

//    @Test
    @DisplayName("글 삭제 실패 테스트")
    void deleteFailTest() {
        // given (기본 데이터 입력)
        Post post = Post.builder()
                .title("title")
                .content("content")
                .build();
        repository.save(post);

        // expected
        assertThrows(PostNotFound.class, () -> {
            postService.delete(post.getId() + 1L);
        });
    }

//    @Test
    @DisplayName("글 수정 실패 테스트")
    void modifyFialTest() {
        // given (기본 데이터 입력)
        Post post = Post.builder()
                .title("title")
                .content("content")
                .build();
        repository.save(post);

        // when (수정할 데이터 입력)
        PostEdit postEdit = PostEdit.builder()
                .title("title1")
                .content("content1")
                .build();

        // expected
        assertThrows(PostNotFound.class, () -> {
            postService.edit(post.getId()+1L, postEdit);
        });
    }

    // @Disabled
    // @Test
//    @DisplayName("Querydsl 서브쿼리 테스트")
//    void subQueryTest() {
//
//        // given
//        List<Post> posts = new ArrayList<>();
//        List<PostEditor> editors = new ArrayList<>();
//
//        Post post1 = Post.builder().title("test1").content("testContent1").build();
//        Post post2 = Post.builder().title("test2").content("testContent2").build();
//        Post post3 = Post.builder().title("test3").content("testContent3").build();
//
//        PostEditor editor1 = PostEditor.builder().title("title1").content("editor1").build();
//        PostEditor editor2 = PostEditor.builder().title("title2").content("editor2").build();
//        PostEditor editor3 = PostEditor.builder().title("title3").content("editor3").build();
//
//        posts.add(post1);
//        posts.add(post2);
//        posts.add(post3);
//        editors.add(editor1);
//        editors.add(editor2);
//        editors.add(editor3);
//
//        repository.saveAll(posts);
//        postEditorRepository.saveAll(editors);
//
//        // when
//        List<PostDto> result = repository.selectEditorContent(post1);
//
//    }

}