package com.busanit501.api5012.service;

import com.busanit501.api5012.dto.TodoDTO;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface TodoService {
    Long register(TodoDTO todoDTO);
}