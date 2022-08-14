package com.test.corretto.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@ToString(of = {"id", "name"})
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 32)
    private String name;

    @OneToMany(mappedBy = "board",cascade = CascadeType.REMOVE)
    private List<Article> articles;

    public Board(String name){
        this.name = name;
    }
}
