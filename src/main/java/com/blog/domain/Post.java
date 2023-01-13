package com.blog.domain;

import lombok.*;

import javax.persistence.*;


//@NoArgsConstructor(access = AccessLevel.PROTECTED)  // Entity : 기본생성자 필요, PROTECTED <- 생성자 접근 불가
@Entity
@Getter
@ToString
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

//    public void change(String title, String content) {
//        this.title = title;
//        this.content = content;
//    }

    public PostEditor.PostEditorBuilder toEditor() {
        return PostEditor.builder()
                .title(title)
                .content(content);

    }

    public void edit(PostEditor postEditor) {
        this.title = postEditor.getTitle();
        this.content = postEditor.getContent();

    }


    /*
    // Entity 에는 서비스의 정책을 절대 넣지말것!!!
    public String getTitle() {
        return this.title.substring(0, 10);
    }
    */

    // public String title;
    // public String content;

}
