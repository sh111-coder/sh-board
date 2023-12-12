package com.shboard.shboard.board.presentation;

import com.shboard.shboard.board.application.BoardService;
import com.shboard.shboard.board.application.dto.BoardsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class BoardApiController {

    private final BoardService boardService;

    @GetMapping
    private ResponseEntity<BoardsResponse> readByPage(final Pageable pageable) {
        final BoardsResponse boardsResponse = boardService.readByPage(pageable);
        return ResponseEntity.ok(boardsResponse);
    }
}
