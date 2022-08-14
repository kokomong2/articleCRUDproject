package com.test.corretto.repository;

import com.test.corretto.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BoardRepository extends JpaRepository<Board,Long> {
}
