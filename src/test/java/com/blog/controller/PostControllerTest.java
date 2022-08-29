package com.blog.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcResultHandlersDsl;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

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
    }

    @Test
    @DisplayName("/posts 요청시 Helo World 출력")
    void test4() throws Exception{       //  application-json
        // expected
        mockMvc.perform(post("/v4/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"제목입니다\", \"content\": \"내용입니다\"}")
                )
                .andExpect(status().isOk())
                .andExpect(content().string("Hello World"))
                .andDo(print());
    }

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
    @DisplayName("/posts 요청시 title 값 필수, 공백 테스트")
    void test8() throws Exception{       //  application-json
        // expected
        mockMvc.perform(post("/v8/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": null, \"content\": \"내용입니다\"}")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value()
                .andDo(print());
    }

}