package com.test.corretto.controller;


import com.test.corretto.dto.ArticleRequestDto;
import com.test.corretto.dto.ArticleResponseDto;
import com.test.corretto.entity.Article;
import com.test.corretto.entity.Board;
import com.test.corretto.repository.ArticleRepository;
import com.test.corretto.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ArticleController {

    private final ArticleService articleService;
    private final ArticleRepository articleRepository;

    //게시물 생성
    @PostMapping("/api/board/{boardId}/article")
    public void createArticle(@RequestBody ArticleRequestDto requestDto, @PathVariable Long boardId){

        articleService.createArticle(requestDto,boardId);
    }

    //생성 날짜를 기준으로 게시글을 가져오는 get
    @GetMapping("/api/board/articles")
    public List<ArticleResponseDto.ArticleList> getArticleList(@RequestParam String createDateTime) {
        return articleService.getArticleList(createDateTime);
    }

    //board 이름 기준 검색
    @GetMapping("/api/board/{boardName}/articles")
    public List<ArticleResponseDto.ArticleList> searchArticleList(@PathVariable String boardName){

        return articleService.searchArticleList(boardName);
    }

    //게시물 상세 api
    @GetMapping("/api/board/article/{articleId}")
    public ArticleResponseDto.Article getArticle(@PathVariable Long articleId){
        return articleService.getArticle(articleId);
    }

    //게시물 삭제
    @DeleteMapping("/api/board/article/{articleId}")
    public void deleteArticle(@PathVariable Long articleId){
        articleRepository.deleteById(articleId);
    }

    //게시물 수정
    @PutMapping("/api/board/article/{articleId}")
    public void putArticle(@PathVariable Long articleId, @RequestBody ArticleRequestDto articleRequestDto){
        articleService.putArticle(articleId,articleRequestDto);
    }
}
