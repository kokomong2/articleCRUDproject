package com.test.corretto.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


public class ArticleResponseDto {

    @Getter
    @Builder
    public static class ArticleList{
        private String boardName;
        private String title;
        private LocalDateTime createdDatetime;
        private String location;
    }

    @Getter
    @Builder
    public static class Article{
        private String boardName;
        private String title;
        private LocalDateTime createdDatetime;
        private List<String> locations;
    }

}
