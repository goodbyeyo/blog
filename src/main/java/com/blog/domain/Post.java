package com.blog.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


//@NoArgsConstructor(access = AccessLevel.PROTECTED)  // Entity : 기본생성자 필요, PROTECTED <- 생성자 접근 불가
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 생성전략
    private Long id;

    private String title;

    @Lob    // long text
    private String content;

    @Builder
    public Post(String title, String content) { // id 는 자동생성
        this.title = title;
        this.content = content;
    }

    // public String title;
    // public String content;

}
