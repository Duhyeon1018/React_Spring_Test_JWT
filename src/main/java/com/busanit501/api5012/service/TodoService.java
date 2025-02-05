package com.busanit501.api5012.service;

import com.busanit501.api5012.dto.PageRequestDTO;
import com.busanit501.api5012.dto.PageResponseDTO;
import com.busanit501.api5012.dto.TodoDTO;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface TodoService {
    Long register(TodoDTO todoDTO);
    TodoDTO read(Long tno);
    PageResponseDTO<TodoDTO> list(PageRequestDTO pageRequestDTO);
    void remove(Long tno);
    void modify(TodoDTO todoDTO);
}