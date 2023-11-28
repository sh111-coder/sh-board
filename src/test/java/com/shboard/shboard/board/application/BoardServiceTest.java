package com.shboard.shboard.board.application;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import java.util.List;

import com.shboard.shboard.board.application.dto.BoardListResponse;
import com.shboard.shboard.board.application.dto.BoardPageResponse;
import com.shboard.shboard.board.application.dto.BoardsResponse;
import com.shboard.shboard.board.domain.Board;
import com.shboard.shboard.board.domain.BoardRepository;
import com.shboard.shboard.member.common.ServiceTest;
import com.shboard.shboard.member.domain.Member;
import com.shboard.shboard.member.domain.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;

class BoardServiceTest extends ServiceTest {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BoardService boardService;


    @Nested
    @DisplayName("게시판 페이지 조회 시")
    class ReadPage {

        private final int pageSize = 2;
        private final int totalPostCount = 7;

        @BeforeEach
        void setUp() {
            final Member member = Member.builder()
                    .loginId("sh111")
                    .password("password1!")
                    .nickname("성하")
                    .build();
            final Member savedMember = memberRepository.save(member);

            for (int i = 1; i <= totalPostCount; i++) {
                final Board board = new Board(savedMember, "title" + i, "content" + i);
                boardRepository.save(board);
            }
        }

        @ParameterizedTest
        @ValueSource(ints = {1, 2, 3})
        @DisplayName("페이지 조회에 성공한다.")
        void success(final int pageNumber) {
            // given
            int expectedTotalPageNumber = 0;
            if (totalPostCount % pageSize == 0) {
                expectedTotalPageNumber = totalPostCount / pageSize;
            } else {
                expectedTotalPageNumber = (totalPostCount / pageSize) + 1;
            }

            // when
            final BoardsResponse boardsResponse = boardService.readByPage(pageNumber, pageSize);
            final List<BoardListResponse> boardListResponses = boardsResponse.boardListResponses();
            final BoardPageResponse boardPageResponse = boardsResponse.boardPageResponse();

            // then
            final int finalExpectedTotalPageNumber = expectedTotalPageNumber;
            assertSoftly(softly -> {
                softly.assertThat(boardListResponses.get(0).id()).isEqualTo((totalPostCount - ((pageNumber - 1) * pageSize)));
                softly.assertThat(boardPageResponse.currentPageNumber()).isEqualTo(pageNumber);
                softly.assertThat(boardPageResponse.totalPageNumber()).isEqualTo(finalExpectedTotalPageNumber);
            });
        }

        @Test
        @DisplayName("게시글이 존재하지 않을 때 조회에 성공한다.")
        void success_not_exist_post() {
            // given
            int emptyCurrentPageNumber = 1;
            int emptyTotalPageNumber = 0;
            boardRepository.deleteAllInBatch();

            // when
            final BoardsResponse boardsResponse = boardService.readByPage(1, 3);
            final List<BoardListResponse> boardListResponses = boardsResponse.boardListResponses();
            final BoardPageResponse boardPageResponse = boardsResponse.boardPageResponse();

            // then
            assertSoftly(softly -> {
                softly.assertThat(boardListResponses).isEmpty();
                softly.assertThat(boardPageResponse.currentPageNumber()).isEqualTo(emptyCurrentPageNumber);
                softly.assertThat(boardPageResponse.totalPageNumber()).isEqualTo(emptyTotalPageNumber);
            });
        }
    }
}
