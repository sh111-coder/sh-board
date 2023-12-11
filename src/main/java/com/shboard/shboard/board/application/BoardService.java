package com.shboard.shboard.board.application;

import com.shboard.shboard.board.application.dto.BoardsResponse;
import com.shboard.shboard.board.domain.Board;
import com.shboard.shboard.board.domain.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional(readOnly = true)
    public BoardsResponse readByPage(final Pageable pageable) {
        final Page<Board> boardPage = boardRepository.findAllByOrderByCreatedAtDesc(pageable);

        return BoardsResponse.of(boardPage, pageable);
    }
}
