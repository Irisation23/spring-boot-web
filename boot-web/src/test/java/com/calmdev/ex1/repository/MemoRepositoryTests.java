package com.calmdev.ex1.repository;

import com.calmdev.ex1.entity.Memo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class MemoRepositoryTests {

    @Autowired
    MemoRepository memoRepository;

    /**
     * 실체 테스트에 앞서 MemoRepository 가 정상적으로 스프링에서 처리되고,
     * 의존성 주입에 문제가 없는지를 확인하는 것이 좋음.
     */
    @Test
    public void testClass() {
        System.out.println(memoRepository.getClass().getName());
    }

    @Test
    public void testInsertDummies() {

        IntStream.rangeClosed(1,100).forEach(i -> {
            Memo memo = Memo.builder().memoText("Sample..." +i).build();
            memoRepository.save(memo);
        });
    }

    @Transactional
    @Test
    public void testSelect() {
        // 데이터 베이스에 존재하는 mno
        Long mno = 100L;

        Optional<Memo> result = memoRepository.findById(mno);
        System.out.println("=======================");

        if(result.isPresent()) {
            Memo memo = result.get();
            System.out.println(memo);
        }
    }

    @Test
    public void testUpdate() {

        Memo memo = Memo.builder()
                .memoText("Update Tesxt")
                .build();
        System.out.println(memoRepository.save(memo));
    }

    @Test
    public void testDelete() {
        Long mno = 100L;

        memoRepository.deleteById(mno);
    }
}
