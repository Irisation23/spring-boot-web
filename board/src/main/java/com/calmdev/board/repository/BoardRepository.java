package com.calmdev.board.repository;

import com.calmdev.board.entity.Board;
import com.calmdev.board.repository.search.SearchBoardRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board,Long>, SearchBoardRepository {
    /**
     *  해당 레파지토리의 해결 방법은 getBoardWithWriter()  는 Board 를 사용하고 있고,
     *  Member를 같이 조회해야 하는 상황임.
     *  Board 클래스에는 Member와의 연관관계를 맺고 있으므로 b.writer 와 같은 형태로 사용.
     *  내부 엔티티 사용에는 'LEFT JOIN' 뒤에 'ON' 을 이용할 필요가 없음.
     *
     */
    //한개의 로우(Object) 내에 Object[] 로 나옴.
    @Query("select b, w from Board b left join b.writer w where b.bno = :bno")
    Object getBoardWithWriter(@Param("bno") Long bno);

    @Query("SELECT b, r FROM Board b LEFT JOIN Reply r ON r.board = b WHERE b.bno = :bno")
    List<Object[]> getBoardWithReply(@Param("bno") Long bno);
}
