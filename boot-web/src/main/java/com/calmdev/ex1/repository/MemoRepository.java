package com.calmdev.ex1.repository;

import com.calmdev.ex1.entity.Memo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemoRepository extends JpaRepository<Memo,Long> {
    List<Memo> findByMnoBetweenOrderByMnoDesc(Long from, Long to); // mno 의 역순으로 정렬하고 싶다
    Page<Memo> findByMnoBetween(Long from, Long to, Pageable pageable);
    void deleteMemoByMnoLessThan(Long num);
}
