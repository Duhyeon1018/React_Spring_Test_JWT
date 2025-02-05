package com.busanit501.api5012.service;

import com.busanit501.api5012.domain.Todo;
import com.busanit501.api5012.dto.TodoDTO;
import com.busanit501.api5012.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public Long register(TodoDTO todoDTO) {
        Todo todo = modelMapper.map(todoDTO, Todo.class); // 오타 수정
        Long tno = todoRepository.save(todo).getTno(); // getTno() 오타 수정
        return tno;
    }

    @Override
    public TodoDTO read(Long tno) {
        Optional<Todo> result = todoRepository.findById(tno);
        Todo todo = result.orElseThrow(); // 예외 발생 시 자동으로 NoSuchElementException 던짐
        return modelMapper.map(todo, TodoDTO.class);
    }
}