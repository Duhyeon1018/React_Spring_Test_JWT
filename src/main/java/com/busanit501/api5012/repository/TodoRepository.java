package com.busanit501.api5012.repository;

import com.busanit501.api5012.domain.Todo;
import com.busanit501.api5012.repository.search.TodoSearch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long>, TodoSearch {
}