package com.calmdev.board.service;

import com.calmdev.board.dto.BoardDTO;
import com.calmdev.board.dto.PageRequestDto;
import com.calmdev.board.dto.PageResultDto;
import com.calmdev.board.entity.Board;
import com.calmdev.board.entity.Member;
import com.calmdev.board.entity.Reply;
import com.calmdev.board.repository.BoardRepository;
import com.calmdev.board.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository;

    @Override
    public Long register(BoardDTO boardDTO) {

        log.info(String.valueOf(boardDTO));

        Board board = dtoToEntity(boardDTO);

        boardRepository.save(board);

        return null;
    }

    @Override
    public PageResultDto<BoardDTO, Object[]> getList(PageRequestDto pageRequestDto) {

        log.info(String.valueOf(pageRequestDto));

        Function<Object[], BoardDTO> fn = (en -> entityToDTO((Board) en[0], (Member) en[1], (Long) en[2]));

        Page<Object[]> result = boardRepository.getBoardWithReplyCount(pageRequestDto.getPageable(Sort.by("bno")
                                                                                                      .descending()));

        return new PageResultDto<>(result, fn);
    }

    @Override
    public BoardDTO get(Long bno) {
        Object result = boardRepository.getBoardByBno(bno);

        Object[] arr = (Object[]) result;

        return entityToDTO((Board) arr[0], (Member) arr[1], (Long) arr[2]);
    }

    @Transactional
    @Override
    public void removeWithReplies(Long bno) {

        replyRepository.deleteByBno(bno);

        boardRepository.deleteById(bno);
    }

    @Override
    public void modify(BoardDTO boardDTO) {

        Board board = boardRepository.getOne(boardDTO.getBno());

        board.changeTitle(boardDTO.getTitle());
        board.changeContent(boardDTO.getContent());

        boardRepository.save(board);
    }

}
