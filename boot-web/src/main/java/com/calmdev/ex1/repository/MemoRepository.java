package com.calmdev.ex1.repository;

import com.calmdev.ex1.entity.Memo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemoRepository extends JpaRepository<Memo,Long> {
}
