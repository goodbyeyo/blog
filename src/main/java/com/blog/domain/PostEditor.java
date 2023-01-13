package com.blog.domain;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.Lob;

// 수정해야 할 필드 선언
@Getter
public class PostEditor {

    private final String title;
    private final String content;

    @Builder
    public PostEditor(String title, String content) {
        this.title = title;     // title != null ? title : this.title;
        this.content = content; // content != null ? content : this.content;
    }
}
