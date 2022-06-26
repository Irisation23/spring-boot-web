package com.calmdev.guestbook.repository;

import com.calmdev.guestbook.entity.Guestbook;
import com.calmdev.guestbook.entity.QGuestbook;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
class GuestbookRepositoryTest {


    @Autowired
    private GuestbookRepository guestbookRepository;

    @Test
    void contextLoads() {
    }

    @Test
    public void insertDummies() {
        IntStream.rangeClosed(1, 300)
                 .forEach(i -> {
                     Guestbook guestbook = Guestbook.builder()
                                                    .title("Title.... " + i)
                                                    .content("Content.... " + i)
                                                    .writer("user" + (i % 10))
                                                    .build();
                     System.out.println(guestbookRepository.save(guestbook));
                 });
    }

    @Test
    public void updateTest() {
        Optional<Guestbook> result = guestbookRepository.findById(300L);
        //존재하는 번호로 테스트

        if (result.isPresent()) {
            Guestbook guestbook = result.get();

            guestbook.changeTitle("Changed Title....");
            guestbook.changeContent("Changed Content!!!!!!");

            guestbookRepository.save(guestbook);
        }
    }

    @Description("해당 메서드는 제목(title)에 1이라는 글자가 있는 엔티티들을 검색한 결과를 보여준다.")
    @Test
    public void testQuery1() {

        Pageable pageable = PageRequest.of(0, 10, Sort.by("gno")
                                                      .descending());
        QGuestbook qGuestbook = QGuestbook.guestbook; // 1

        String keyword = "1";

        BooleanBuilder builder = new BooleanBuilder(); // 2

        BooleanExpression expression = qGuestbook.title.contains(keyword);

        builder.and(expression); // 4

        Page<Guestbook> result = guestbookRepository.findAll(builder, pageable);

        result.stream()
              .forEach(guestbook -> {
                  System.out.println(guestbook);
              });
    }

    /**
     * 해당 테스트는 제목(title) or 내용(content) 에 특정한 키워드(keyword) 가 있고
     * gno 가 0 보다 크다 와 같은 조건을 처리하기 위한 테스트 이다.
     */
    @Description("다중 항목 검색 테스트")
    @Test
    public void testQuery2() {

        Pageable pageable = PageRequest.of(0, 15, Sort.by("gno")
                                                      .descending());

        QGuestbook qGuestbook = QGuestbook.guestbook;

        String keyword = "1";

        BooleanBuilder builder = new BooleanBuilder();

        BooleanExpression exTitle = qGuestbook.title.contains(keyword);

        BooleanExpression exContent = qGuestbook.content.contains(keyword);

        BooleanExpression exAll = exTitle.or(exContent); // 1------------------

        builder.and(exAll); // 2------------------

        builder.and(qGuestbook.gno.gt(0L)); // 3----------------------

        Page<Guestbook> result = guestbookRepository.findAll(builder, pageable);

        result.stream()
              .forEach(guestbook -> {
                  System.out.println(guestbook);
              });
    }
}