package com.blog.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PostSearch {

    private static final int MAX_SIZE = 2000;
    @Builder.Default    // 생성자 레벨이 아니라 클레스 레벨에서 Builder를 쓸때 적용된다
    private Integer page = 1;

    @Builder.Default
    private Integer size = 10;

    public long getOffset() {
        return (long)(Math.max(1, page) - 1) * Math.min(size, MAX_SIZE);
    }


//    @Builder
//    public PostSearch(Integer page, Integer size) {
//        this.page = page;
//        this.size = size;
//    }
}
