package com.shboard.shboard.board.presentation;

import java.net.URI;

import com.shboard.shboard.board.application.BoardService;
import com.shboard.shboard.board.application.dto.BoardWriteRequest;
import com.shboard.shboard.board.application.dto.BoardsResponse;
import com.shboard.shboard.global.auth.AuthMember;
import com.shboard.shboard.global.auth.AuthMemberId;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class BoardApiController {

    private final BoardService boardService;

    @PostMapping
    private ResponseEntity<Void> write(@AuthMember final AuthMemberId authMemberId, final BoardWriteRequest request) {
        final Long savedBoardId = boardService.writeBoard(authMemberId.id(), request);
        return ResponseEntity.created(URI.create("/boards/" + savedBoardId)).build();
    }

    @GetMapping
    private ResponseEntity<BoardsResponse> readByPage(final Pageable pageable) {
        final BoardsResponse boardsResponse = boardService.readByPage(pageable);
        return ResponseEntity.ok(boardsResponse);
    }

    @GetMapping("/search")
    private ResponseEntity<BoardsResponse> searchByCondition(
            @RequestParam(required = false) final String title,
            @RequestParam(required = false) final String writer,
            final Pageable pageable
    ) {
        final BoardsResponse boardsResponse = boardService.searchByCondition(title, writer, pageable);
        return ResponseEntity.ok(boardsResponse);
    }
}
