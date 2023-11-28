package com.shboard.shboard.board.presentation;

import com.shboard.shboard.board.application.BoardService;
import com.shboard.shboard.board.application.dto.BoardsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardApiController {

    private final BoardService boardService;

    @GetMapping
    private ResponseEntity<BoardsResponse> readByPage(@RequestParam final int page, @RequestParam final int size) {
        final BoardsResponse boardsResponse = boardService.readByPage(page, size);
        return ResponseEntity.ok(boardsResponse);
    }
}
