package com.calmdev.board.repository;

import com.calmdev.board.entity.Board;
import com.calmdev.board.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void testInsertBoard() {

        IntStream.rangeClosed(1, 100)
                 .forEach(i -> {

                     Member member = Member.builder()
                                           .email("user" + i + "@aaa.com")
                                           .build();

                     Board board = Board.builder()
                                        .title("Title..." + i)
                                        .content("Content..." + i)
                                        .writer(member)
                                        .build();

                     boardRepository.save(board);
                 });
    }

    @Description("@ManyToOne 으로 참조하고 있는 Board 를 조회하는 테스트 코드")
    @Transactional
    @Test
    public void testRead() {

        Board result = boardRepository.findById(100L).orElseThrow();

        System.out.println(result);
        System.out.println(result.getWriter());
    }

    @Description("LeftJoin 사용")
    @Test
    public void testReadWithWriter() {
        Object result = boardRepository.getBoardWithWriter(100L);

        Object[] arr = (Object[]) result;

        System.out.println("----------------------------");
        System.out.println(Arrays.toString(arr));
    }

    @Description("Board 가 참조하고 있지 않은 상황에서의 조인")
    @Test
    public void testGetBoardWithReply() {

        List<Object[]> result = boardRepository.getBoardWithReply(100L);

        for (Object[] arr : result) {
            System.out.println(Arrays.toString(arr));
        }
    }

    @Description("JPQLQuery 설정 확인.")
    @Test
    public void testSearch1() {
        boardRepository.search1();
    }

    @Test
    public void testSearchPage() {

        Pageable pageable = PageRequest.of(0,10, Sort.by("bno").descending());

        Page<Object[]> result = boardRepository.searchPage("t", "1", pageable);
    }

}