package com.blog.controller;

import com.blog.request.PostCreate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

// SSR -> jsp, thymeleaf, mustache, freemarker -> html, rendering
// SPA -> vue -> javascript + api
// Http Method : GET, POST, PUT, PATCH, DELETE, OPTIONS, HEAD, TRACE, CONNECT
// 데이터 검증 이유
// 1. client 개발자가 깜빡할수 있다 (실수로 값을 안보낼수 있다)
// 2. client bug 로 값이 누락될수 있다
// 3. 외부에 나쁜 사람이 값을 임의로 조작해서 보낼수 있다
// 4. DB에 값을 저장할 때 의도치 않은 오류가 발생할수 있다
// 5. 서버개발자의 편안함을 위해서

@RestController
@Slf4j
public class PostController {

    @PostMapping("/v1/posts")
    public String v1post(@RequestParam String title, @RequestParam String content) {
        log.info("title={}, content={}", title, content);
        return "Hello World";
    }

    @PostMapping("/v2/posts")
    public String v2post(@RequestParam Map<String, String> params) {
        log.info("params={}", params);
        String title = params.get("title");
        log.info("title={}", title);
        return "Hello World";
    }

    @PostMapping("/v3/posts")
    public String v3post(PostCreate params) {
        log.info("params={}", params.toString());
        return "Hello World";
    }

    @PostMapping("/v4/posts")
    public String v4post(@RequestBody PostCreate params) {
        log.info("params={}", params.toString());
        return "Hello World";
    }

    @PostMapping("/v5/posts")
    public String v5post(@RequestBody PostCreate params) throws Exception {
        log.info("params={}", params.toString());
        String title = params.getTitle();
        if (title == null || title.equals("")) {
            throw new Exception("not exist title");
        }
        String content = params.getContent();
        if (content == null || content.equals("")) {
            throw new Exception("not exist content");
        }
        return "Hello World";
    }

}
