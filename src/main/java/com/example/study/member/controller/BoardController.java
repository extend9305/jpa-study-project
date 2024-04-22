package com.example.study.member.controller;

import com.example.study.member.domain.item.Board;
import com.example.study.member.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/board")
public class BoardController {
    @Autowired
    private BoardRepository boardRepository;

    @RequestMapping("/save")
    public void save(){
        Board board = new Board();
        //board.setData("TEST");
        boardRepository.save(board);
    }
}
