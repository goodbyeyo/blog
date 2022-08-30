package com.blog.request;

import lombok.*;

import javax.validation.constraints.NotBlank;

// 빌더의 장점 :
// 1) 가독성(값 생성에 유연함),
// 2) 필요한 값만 받을수 있음(오버로딩 가능한 조건),
// 3) 객체의 불변성
@Setter
@Getter
@ToString
//@Builder  // 클래스위에 추가하는것도 가능하지만 비추천
//@NoArgsConstructor
public class PostCreate {

    @NotBlank(message = "타이틀 입력하세요")
//    private String title;
    private final String title; // final 선언하면 value setting unable

    @NotBlank(message = "컨텐츠 입력하세요")
//    private String content;
    private final String content;

    @Builder    // 생성자 위에 @Builder 어노테이션 추가하는것을 추천
    public PostCreate(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public PostCreate changeTitle(String title) {
        return PostCreate.builder()
                .title(title)
                .content(this.content)
                .build();
    }

//    public PostCreate(String title, String content) {
//        this.title = title;
//        this.content = content;
//    }
}
