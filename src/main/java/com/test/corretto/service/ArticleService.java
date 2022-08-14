package com.test.corretto.service;


import com.querydsl.jpa.impl.JPAQueryFactory;
import com.test.corretto.dto.ArticleRequestDto;
import com.test.corretto.dto.ArticleResponseDto;
import com.test.corretto.entity.*;
import com.test.corretto.repository.ArticleRepository;
import com.test.corretto.repository.AttachmentRepository;
import com.test.corretto.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.test.corretto.entity.QArticle.article;
import static com.test.corretto.entity.QAttachment.attachment;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final BoardRepository boardRepository;
    private final ArticleRepository articleRepository;
    private final AttachmentRepository attachmentRepository;

    @PersistenceContext
    EntityManager em;


    @Transactional
    public void createArticle(ArticleRequestDto requestDto, Long boardId) {

        Board board = boardRepository.findById(boardId).orElseThrow(()-> new IllegalArgumentException("해당 게시판이 없음"));
        List<Attachment> attachments = new ArrayList<>();
        Article article = Article.builder()
                .board(board)
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .viewCount(requestDto.getViewCount())
                .attachments(attachments)
                .build();

        for (int i = 0; i < 3; i++) {
            Attachment attachment = new Attachment(article,i+1 + "번째 파일 경로");
            article.add(attachment);
            attachmentRepository.save(attachment);
        }
        articleRepository.save(article);

    }

    public List<ArticleResponseDto.ArticleList> getArticleList(String createDateTime) {

        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        if(Objects.equals(createDateTime, "startDateTime")){
            List<Article> articles = queryFactory
                    .selectFrom(article)
                    .orderBy(article.createdDatetime.asc())
                    .fetch();
            return getArticleResponseDtos(queryFactory, articles);
        }else if(Objects.equals(createDateTime, "endDateTime")){
            List<Article> articles = queryFactory
                    .selectFrom(article)
                    .orderBy(article.createdDatetime.desc())
                    .fetch();
            return getArticleResponseDtos(queryFactory, articles);
        }else {throw new IllegalArgumentException("날짜 기준 입력값이 잘못됐습니다.");}

    }

    public List<ArticleResponseDto.ArticleList> searchArticleList(String boardName) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        List<Article> articles = queryFactory
                .selectFrom(article)
                .where(article.board.name.contains(boardName))
                .fetch();
        return getArticleResponseDtos(queryFactory, articles);

    }

    private List<ArticleResponseDto.ArticleList> getArticleResponseDtos(JPAQueryFactory queryFactory, List<Article> articles) {
        List<ArticleResponseDto.ArticleList> articleResponseDtos = new ArrayList<>();
        for (Article article : articles) {
            //가장 처음으로 넣은 location 가져오기
            String location = queryFactory
                    .select(attachment.location)
                    .from(attachment)
                    .orderBy(attachment.createdDatetime.asc())
                    .limit(1)
                    .fetchOne();
            System.out.println("location : " +location);

            ArticleResponseDto.ArticleList articleResponseDto = ArticleResponseDto.ArticleList.builder()
                    .boardName(article.getBoard().getName())
                    .title(article.getTitle())
                    .createdDatetime(article.getCreatedDatetime())
                    .location(location)
                    .build();
            articleResponseDtos.add(articleResponseDto);
            System.out.println("리스트에 추가");
        }
        return articleResponseDtos;
    }

    public ArticleResponseDto.Article getArticle(Long articleId) {
        Article article = articleRepository.findById(articleId).orElseThrow(()->new IllegalArgumentException("해당 게시글이 없음"));
        List<String> locations = new ArrayList<>();
        List<Attachment> attachments = article.getAttachments();
        for (Attachment attachment : attachments) {
            locations.add(attachment.getLocation());
        }
        System.out.println("location 전부 넣음");
        return ArticleResponseDto.Article.builder()
                .boardName(article.getBoard().getName())
                .title(article.getTitle())
                .createdDatetime(article.getCreatedDatetime())
                .locations(locations)
                .build();
    }

    public void putArticle(Long articleId, ArticleRequestDto articleRequestDto) {

        Article article = articleRepository.findById(articleId).orElseThrow(()-> new IllegalArgumentException("해당 게시글이 없음"));
        String title = articleRequestDto.getTitle();
        String content = articleRequestDto.getContent();
        if(!Objects.equals(title, article.getTitle())){
            article.updateTitle(title);
        }
        if(!Objects.equals(content, article.getContent())){
            article.updateContent(content);
        }
        articleRepository.save(article);

    }
}
