package com.test.corretto.entity;


import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = {"id", "title","content" , "viewCount"})
public class Article extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="board_id")
    private Board board;

    @Column(length = 128)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private int viewCount;

    @OneToMany(mappedBy = "article", cascade = CascadeType.REMOVE)
    private List<Attachment> attachments;

        public void add(Attachment attachment){
            this.attachments.add(attachment);
    }

    public Article(Board board,String title, String content, int viewCount){
        this.board = board;
        this.title = title;
        this.content = content;
        this.viewCount = viewCount;
    }

    public void updateTitle(String title){
            this.title = title;
    }
    public void updateContent(String content){
            this.content = content;
    }



}
