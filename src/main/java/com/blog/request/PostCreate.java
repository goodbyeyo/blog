package com.blog.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@ToString
public class PostCreate {

    @NotBlank(message = "타이틀 입력하세요")
    private String title;

    @NotBlank(message = "컨텐츠 입력하세요")
    private String content;
}
