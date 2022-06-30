package com.calmdev.board.repository;

import com.calmdev.board.entity.Board;
import com.calmdev.board.entity.Reply;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
class ReplyRepositoryTest {

    @Autowired
    private ReplyRepository replyRepository;

    @Test
    public void insertReply() {
        IntStream.rangeClosed(1, 300)
                 .forEach(i -> {
                     // 1부터 100까지의 임의의 번호
                     long bno = (long) (Math.random() * 100) + 1;

                     Board board = Board.builder()
                                        .bno(bno)
                                        .build();

                     Reply reply = Reply.builder()
                             .text("Reply ...... " + i)
                             .board(board)
                             .replyer("guest")
                                        .build();

                     replyRepository.save(reply);
                 });
    }
}