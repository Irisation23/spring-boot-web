package com.calmdev.board.service;

import com.calmdev.board.dto.BoardDTO;
import com.calmdev.board.dto.PageRequestDto;
import com.calmdev.board.dto.PageResultDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardServiceImplTest {

    @Autowired
    private BoardService boardService;

    @Test
    void testRegister() {

        BoardDTO boardDTO = BoardDTO.builder()
                .title("Test.")
                .content("TestContent")
                .writerEmail("user55@aaa.com") // 현재 데이터 베이스에 존재하는 회원 이메일
                                    .build();

        Long bno = boardService.register(boardDTO);
    }

    @Description("목록 처리 테스트")
    @Test
    public void testGetList() {

        PageRequestDto pageRequestDto = new PageRequestDto();

        PageResultDto<BoardDTO, Object[]> result = boardService.getList(pageRequestDto);

        for (BoardDTO boardDTO : result.getDtoList()) {
            System.out.println(boardDTO);
        }
    }

    @Description("게시물 조회 테스트")
    @Test
    public void testGet() {

        Long bno = 99L;

        BoardDTO boardDTO = boardService.get(bno);

        System.out.println(boardDTO);
    }

    @Description("게시물 삭제 테스트")
    @Test
    public void testRemove() {
        Long bno = 1L;
        boardService.removeWithReplies(bno);
    }

    @Description("게시물 수정 테스트")
    @Test
    public void testModify() {

        BoardDTO boardDTO = BoardDTO.builder()
                .bno(2L)
                .title("제목 변경합니다.")
                .content("내용 변경합니다.")
                                    .build();
    }
}