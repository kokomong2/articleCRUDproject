package com.test.corretto.controller;


import com.test.corretto.entity.Board;
import com.test.corretto.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardRepository boardRepository;

    @PostMapping("/api/board")
    public void createBoard(@RequestParam String name){
        Board board = new Board(name);
        boardRepository.save(board);
    }
}
