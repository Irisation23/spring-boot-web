package com.calmdev.board.service;

import com.calmdev.board.dto.BoardDTO;
import com.calmdev.board.dto.PageRequestDto;
import com.calmdev.board.dto.PageResultDto;
import com.calmdev.board.entity.Board;
import com.calmdev.board.entity.Member;

public interface BoardService {

    Long register(BoardDTO boardDTO);

    PageResultDto<BoardDTO, Object[]> getList(PageRequestDto pageRequestDto);

    BoardDTO get(Long bno);

    void removeWithReplies(Long bno); // 삭제 기능

    default Board dtoToEntity(BoardDTO dto) {
        return Board.builder()
                    .bno(dto.getBno())
                    .title(dto.getTitle())
                    .content(dto.getContent())
                    .writer(Member.builder()
                                  .email(dto.getWriterEmail())
                                  .build())
                    .build();
    }

    default BoardDTO entityToDTO(Board board, Member member, Long replyCount) {

        BoardDTO boardDTO = BoardDTO.builder()
                .writerEmail(member.getEmail())
                .modDate(board.getModDate())
                .regDate(board.getRegDate())
                .bno(board.getBno())
                .writerName(member.getName())
                .replyCount(replyCount.intValue()) // long 이 나오므로 int 로 처리 해야함.
                .title(board.getTitle())
                .content(board.getContent())
                                    .build();

        return boardDTO;
    }

    void modify(BoardDTO boardDTO);

}
