package com.busanit501.api5012.controller;

import com.busanit501.api5012.dto.TodoDTO;
import com.busanit501.api5012.service.TodoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/todo")
@Log4j2
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Long> register(@RequestBody TodoDTO todoDTO) {
        log.info("Received Todo: {}", todoDTO);

        // 실제로 todoService.register(todoDTO)를 호출하여 저장된 tno를 반환하도록 수정
        Long tno = todoService.register(todoDTO);
        return Map.of("tno", tno);
    }

}
