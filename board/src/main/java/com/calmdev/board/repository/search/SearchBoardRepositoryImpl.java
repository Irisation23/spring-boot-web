package com.calmdev.board.repository.search;

import com.calmdev.board.entity.Board;
import com.calmdev.board.entity.QBoard;
import com.calmdev.board.entity.QMember;
import com.calmdev.board.entity.QReply;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

@Slf4j
public class SearchBoardRepositoryImpl
        extends QuerydslRepositorySupport
        implements SearchBoardRepository {

    public SearchBoardRepositoryImpl() {
        super(Board.class);
    }

    @Override
    public Board search1() {

        log.info("쿼리 dsl 설정 문제 없는지 확인 하는중");

        QBoard board = QBoard.board;
        QReply reply = QReply.reply;
        QMember member = QMember.member;

        JPQLQuery<Board> jpqlQuery = from(board);
        jpqlQuery.leftJoin(member).on(board.writer.eq(member));
        jpqlQuery.leftJoin(reply).on(reply.board.eq(board));

        JPQLQuery<Tuple> tuple = jpqlQuery.select(board, member.email, reply.count());

        tuple.groupBy(board);

        jpqlQuery.select(board, member.email, reply.count())
                         .groupBy(board);

        log.info("시작");
        //log.info(String.valueOf(jpqlQuery));
        List<Tuple> result = tuple.fetch();

        log.info(String.valueOf(result));

        log.info("종료");

        return null;
    }

    @Override
    public Page<Object[]> searchPage(String type, String keyword, Pageable pageable) {

        QBoard board = QBoard.board;
        QReply reply = QReply.reply;
        QMember member = QMember.member;

        JPQLQuery<Board> jpqlQuery = from(board);
        jpqlQuery.leftJoin(member).on(board.writer.eq(member));
        jpqlQuery.leftJoin(reply).on(reply.board.eq(board));

        /**
         * SELECT b, w, count(r) FROM Board b
         * LEFT JOIN b.writer w LEFT JOIN Reply r ON r.board = b
         */
        JPQLQuery<Tuple> tuple = jpqlQuery.select(board, member, reply.count());

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        BooleanExpression expression = board.bno.gt(0L);

        booleanBuilder.and(expression);

        if(type != null) {
            String[] typeArr = type.split("");
            BooleanBuilder conditionBuilder = new BooleanBuilder();

            for (String t : typeArr) {
                switch (t) {
                    case "t" :
                        conditionBuilder.or(board.title.contains(keyword));
                        break;

                    case "w" :
                        conditionBuilder.or(member.email.contains(keyword));
                        break;

                    case "c" :
                        conditionBuilder.or((board.content.contains(keyword)));
                        break;
                }
            }
            booleanBuilder.and(conditionBuilder);
        }

        tuple.where(booleanBuilder);

        tuple.groupBy(board);

        List<Tuple> result = tuple.fetch();

        log.info(String.valueOf(result));

        log.info("해당 메서드 확인!");

        return null;
    }
}
