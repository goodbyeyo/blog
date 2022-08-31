package com.blog.controller;

import com.blog.domain.Post;
import com.blog.repository.PostRepository;
import com.blog.request.PostCreate;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcResultHandlersDsl;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@WebMvcTest // 간단한 웹 레이어 테스트
@AutoConfigureMockMvc
@SpringBootTest // MockMvc bean 주입 불가능 -> 따라서 @WebMvcTest 어노테이션 안에 있는 @AutoConfigureMockMvc
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepository repository;

    @Autowired
    ObjectMapper objectMapper;  // Spring 에서 제공

    @BeforeEach // 각각의 테스트 메서드가 실행되기전에 실행
    void clean() {
        repository.deleteAll(); // 각각 테스트 실행후 데이터 삭제
    }

    @Test
    @DisplayName("/posts 요청시 Hello World 출력")
    void test4() throws Exception{       //  application-json
        // given
        // PostCreate request = new PostCreate("제목입니다", "내용입니다");
        PostCreate request = PostCreate.builder()
                .title("제목입니다")
                .content("내용입니다")
                .build();
        String jsonString = objectMapper.writeValueAsString(request);
        System.out.println("jsonString = " + jsonString);

        // expected
        mockMvc.perform(post("/v4/posts")
                        .contentType(APPLICATION_JSON)
                        .content(jsonString)
                        // .content("{\"title\": \"제목입니다\", \"content\": \"내용입니다\"}")
                )
                .andExpect(status().isOk())
                .andExpect(content().string("Hello World"))
                .andDo(print());
    }

    @Test
    @DisplayName("/v10/posts 요청시 title 값 필수(에러 필드를 Custom 정의하여 리턴받는 테스트)")
    void test10() throws Exception{
        // given
        PostCreate request = PostCreate.builder()
                .content("내용입니다")
                .build();
        String jsonString = objectMapper.writeValueAsString(request);

        // expected
        mockMvc.perform(post("/v10/posts")
                        .contentType(APPLICATION_JSON)
                        .content(jsonString)
                        // .content("{\"title\": null, \"content\": \"내용입니다\"}")
                        // .content("{\"title\": \"ㅇㅇ\", \"content\": \"내용입니다\"}")
                )
                // .andExpect(status().isOk())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다"))
                .andExpect(jsonPath("$.validation.title").value("타이틀 입력하세요"))
                .andDo(print());
    }

    @Test
    @DisplayName("/posts 요청 시 DB에 값이 저장된다")
    void test11() throws Exception{
        // given
        PostCreate request = PostCreate.builder()
                .title("제목입니다")
                .content("내용입니다")
                .build();
        String jsonString = objectMapper.writeValueAsString(request);
        // when
        mockMvc.perform(post("/posts")
                        .contentType(APPLICATION_JSON)
                        .content(jsonString)
                        // .content("{\"title\": \"제목입니다\", \"content\": \"내용입니다\"}")
                )
                .andExpect(status().isOk())
                .andDo(print());
        // then
        Assertions.assertEquals(1L, repository.count());

        Post post = repository.findAll().get(0);
        assertEquals("제목입니다",post.getTitle());
        assertEquals("내용입니다",post.getContent());

    }

    @Test
    @DisplayName("단건 조회")
    void selectOneTest() throws Exception {
        // given
        Post post = Post.builder()
                .title("foo11112222333344444")
                .content("bar")
                .build();
        repository.save(post);

        // expected(when & then)
        mockMvc.perform(get("/posts/{postId}", post.getId())
                                .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(post.getId()))
                .andExpect(jsonPath("$.title").value("foo1111222"))
                .andExpect(jsonPath("$.content").value("bar"))
                .andDo(print());
    }



    @Test
    @DisplayName("다건 조회")
    void selectListTest() throws Exception {
        // given
        Post post1 = Post.builder()
                .title("title_1")
                .content("content_1")
                .build();

        repository.save(post1);

        Post post2 = Post.builder()
                .title("title_2")
                .content("content_2")
                .build();
        repository.save(post2);

        // expected(when & then)
        mockMvc.perform(get("/posts")
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", Matchers.is(2)))
                .andExpect(jsonPath("$[0].id").value(post1.getId()))
                .andExpect(jsonPath("$[0].title").value("title_1"))
                .andExpect(jsonPath("$[0].content").value("content_1"))
                .andDo(print());
    }

    @Test
    @DisplayName("다건 조회")
    void selectListTest1() throws Exception {
        // given
        Post post1 = Post.builder()
                .title("title_1")
                .content("content_1")
                .build();

        repository.save(post1);

        Post post2 = Post.builder()
                .title("title_2")
                .content("content_2")
                .build();
        repository.save(post2);

        // expected(when & then)
        mockMvc.perform(get("/posts")
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", Matchers.is(2)))
                .andExpect(jsonPath("$[0].id").value(post1.getId()))
                .andExpect(jsonPath("$[0].title").value("title_1"))
                .andExpect(jsonPath("$[0].content").value("content_1"))
                .andDo(print());
    }

    @Test
    @DisplayName("다건 조회 개선 1")
    void selectListTest2() throws Exception {
        // given
        Post post1 = repository.save(Post.builder()
                .title("title_1")
                .content("content_1")
                .build()
        );

        Post post2 = repository.save(Post.builder()
                .title("title_2")
                .content("content_2")
                .build()
        );

        // expected(when & then)
        mockMvc.perform(get("/posts")
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", Matchers.is(2)))
                .andExpect(jsonPath("$[0].id").value(post1.getId()))
                .andExpect(jsonPath("$[0].title").value("title_1"))
                .andExpect(jsonPath("$[0].content").value("content_1"))
                .andExpect(jsonPath("$[1].id").value(post2.getId()))
                .andExpect(jsonPath("$[1].title").value("title_2"))
                .andExpect(jsonPath("$[1].content").value("content_2"))
                .andDo(print());
    }

    @Test
    @DisplayName("다건 조회 개선 코드")
    void selectListTest3() throws Exception {
        // given
        List<Post> postList = repository.saveAll(List.of(
                Post.builder()
                        .title("title_1")
                        .content("content_1")
                        .build(),
                Post.builder()
                        .title("title_2")
                        .content("content_2")
                        .build()
        ));

        // expected(when & then)
        mockMvc.perform(get("/posts")
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", Matchers.is(2)))
                .andExpect(jsonPath("$[0].id").value(postList.get(0).getId()))
                .andExpect(jsonPath("$[0].title").value("title_1"))
                .andExpect(jsonPath("$[0].content").value("content_1"))
                .andExpect(jsonPath("$[1].id").value(postList.get(1).getId()))
                .andExpect(jsonPath("$[1].title").value("title_2"))
                .andExpect(jsonPath("$[1].content").value("content_2"))
                .andDo(print());
    }

    @Test
    @DisplayName("다건 조회 페이징 처리")
    void selectListOrderByTest() throws Exception {
        // given
        List<Post> requestList = IntStream.range(1, 31) // for (int i = 0; i < 30; i++) {
                .mapToObj(i-> Post.builder()
                        .title("title " + i)
                        .content("content " + i)
                        .build())
                .collect(Collectors.toList());
        repository.saveAll(requestList);

        // expected(when & then)
         mockMvc.perform(get("/posts?page=1&sort=id,desc&size=5")
         // mockMvc.perform(get("/posts?page=1&sort=id,desc")
                        .contentType(APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", Matchers.is(5)))
                 .andExpect(jsonPath("$[0].id").value(30))
                 .andExpect(jsonPath("$[0].title").value("title 30"))
                 .andExpect(jsonPath("$[0].content").value("content 30"))
                .andDo(print());
    }




    /*
    @Test
    @DisplayName("/posts 요청시 Helo World 출력")
    void test1() throws Exception{       // application/x-www-form-urlencoded
        // expected
        mockMvc.perform(post("/v1/posts")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                        .param("title", "글제목")
                        .param("content","글내용")
                )
                .andExpect(status().isOk())
                .andExpect(content().string("Hello World"))
                .andDo(print());
    }

    @Test
    @DisplayName("/posts 요청시 Helo World 출력")
    void test2() throws Exception{       // application/x-www-form-urlencoded
        // expected
        mockMvc.perform(post("/v2/posts")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                        .param("title", "글제목")
                        .param("content","글내용")
                )
                .andExpect(status().isOk())
                .andExpect(content().string("Hello World"))
                .andDo(print());
    }

    @Test
    @DisplayName("/posts 요청시 Helo World 출력")
    void test3() throws Exception{       // application/x-www-form-urlencoded
        // expected
        mockMvc.perform(post("/v3/posts")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                        .param("title", "글제목")
                        .param("content","글내용")
                )
                .andExpect(status().isOk())
                .andExpect(content().string("Hello World"))
                .andDo(print());
    }*/

    /*
    @Test
    @DisplayName("/posts 요청시 title 값은 필수")
    void test5() throws Exception{       //  application-json
        // expected
        mockMvc.perform(post("/v5/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"\", \"content\": \"내용입니다\"}")
                )
                .andExpect(status().isOk())
                .andExpect(content().string("Hello World"))
                .andDo(print());
    }

    @Test
    @DisplayName("/posts 요청시 title 값 필수, 공백 테스트")
    void test6() throws Exception{       //  application-json
        // expected
        mockMvc.perform(post("/v6/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"       \", \"content\": \"내용입니다\"}")
                )
                .andExpect(status().isOk())
                .andExpect(content().string("Hello World"))
                .andDo(print());
    }

    @Test
    @DisplayName("/posts 요청시 title 값 필수, 공백 테스트")
    void test7() throws Exception{       //  application-json
        // expected
        mockMvc.perform(post("/v7/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"       \", \"content\": \"내용입니다\"}")
                )
                .andExpect(status().isOk())
                .andExpect(content().string("Hello World"))
                .andDo(print());
    }

    @Test
    @DisplayName("jsonPath 테스트")
    void test8() throws Exception{       //  application-json
        // expected
        mockMvc.perform(post("/v8/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": null, \"content\": \"내용입니다\"}")
                )
                .andExpect(status().isOk())
                // junit5 jsonPath , object, array 검증방법 공부 필요...
                .andExpect(jsonPath("$.title").value("타이틀 입력하세요"))
                .andDo(print());
    }

    @Test
    @DisplayName("에러 필드를 Map으로 리턴받는 테스트")
    void test9() throws Exception{
        // expected
        mockMvc.perform(post("/v9/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": null, \"content\": \"내용입니다\"}")
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value("타이틀 입력하세요"))
                .andDo(print());
    }
    */

}