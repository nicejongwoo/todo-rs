package com.example.todo.service;

import com.example.todo.entity.Todo;
import com.example.todo.persistence.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    public List<Todo> create(final Todo todo) {
        //validations
        validate(todo);

        //save
        todoRepository.save(todo);
        log.info("Entity Id: {} is saved", todo.getId());

        //return
        return todoRepository.findByUserId(todo.getUserId());
    }

    public List<Todo> retrieve(final String userId) {
        return todoRepository.findByUserId(userId);
    }

    private void validate(Todo todo) {
        if (todo == null) {
            log.warn("Entity cannot be null");
            throw new RuntimeException("Entity cannot be null");
        }

        if (todo.getUserId() == null) {
            log.warn("Unknown user");
            throw new RuntimeException("Unknown user");
        }
    }

}
