package com.blog.controller;

import com.blog.domain.Post;
import com.blog.exception.InvalidRequest;
import com.blog.request.PostCreate;
import com.blog.request.PostEdit;
import com.blog.request.PostSearch;
import com.blog.response.PostResponse;
import com.blog.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
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

// spring boot restDocs
// - 운영코드에 영향이 없다
// - 코드 수정 -> 문서를 수정 x -> 코드(기능) -> 문서
// - 테스트 케이스 실행 - 문서를 생성해줌ㄴ다

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/posts")
    public void post(@RequestBody @Valid PostCreate request) {
        request.validate();
        postService.write(request);
    }

    @GetMapping("/posts/{postId}")
    public PostResponse get(@PathVariable(name = "postId") Long id) {
        return postService.get(id);
    }

    @GetMapping("/posts")
    public List<PostResponse> getList(@ModelAttribute PostSearch postSearch) {
        return postService.getList(postSearch);
    }

    @PatchMapping("/posts/{postId}")
    public void edit(@PathVariable Long postId, @RequestBody @Valid PostEdit request) {
        postService.edit(postId, request);
    }

    @DeleteMapping("/posts/{postId}")
    public void delete(@PathVariable Long postId) {
        postService.delete(postId);
    }



    // 여러개의 글 조회
//    @GetMapping("v1/posts")
//    public List<PostResponse> getLists(@RequestParam int page) {
//        return postService.getList(page);
//    }

    // 여러개의 글 조회
//    @GetMapping("/posts")
//    // public List<PostResponse> getList(@PageableDefault(size = 5) Pageable pageable) {
//    public List<PostResponse> getList(/*@PageableDefault*/ Pageable pageable) {
//        return postService.getList(pageable);
//    }



    /*
    @PostMapping("/posts")
    public Map<String, String> post(@RequestBody @Valid PostCreate request) {
        postService.write(request);
        return Map.of();
    }
    */

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

    @PostMapping("/v6/posts")   // @Valid 체크
    public String v6post(@RequestBody @Valid PostCreate params) throws Exception {
        log.info("params={}", params.toString());
        return "Hello World";
    }

    @PostMapping("/v7/posts")
    public Map<String, String> v7post(@RequestBody @Valid PostCreate params, BindingResult bindingResult ) throws Exception {
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            FieldError fieldError = fieldErrors.get(0);
            String fieldName = fieldError.getField();
            String errorMessage = fieldError.getDefaultMessage();

            Map<String, String> error = new HashMap<>();
            error.put(fieldName, errorMessage);
            return error;
        }
        return Map.of(); // Java8 , 11 부터 지원...
    }

    @PostMapping("/v8/posts")
    public Map<String, String> v8post(@RequestBody @Valid PostCreate params, BindingResult bindingResult ) throws Exception {
        // 아래의 방법의 단점
        // 1. 매번 메서드마다 값을 검증해야 한다(빠뜨리거나 반복되는 코드)
        // 2. 응답값에 HashMap 객체를 리턴 x : 응답클래스를 만들어 주는게 좋다
        // 3. 여러개의 에러처리가 힘들다
        // 4. 세번 이상의 반복작업은 피하는게 좋다
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            FieldError fieldError = fieldErrors.get(0);
            String fieldName = fieldError.getField();
            String errorMessage = fieldError.getDefaultMessage();

            Map<String, String> error = new HashMap<>();
            error.put(fieldName, errorMessage);
            return error;
        }
        return Map.of(); // Java8 , 11 부터 지원...
    }

    @PostMapping("/v9/posts")
    public Map<String, String> v9post(@RequestBody @Valid PostCreate params) {
        return Map.of();
    }

    @PostMapping("/v10/posts")
    public Map<String, String> v10post(@RequestBody @Valid PostCreate request) {
        return Map.of();
    }

}
