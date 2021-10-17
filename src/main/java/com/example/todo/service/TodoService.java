package com.example.todo.service;

import com.example.todo.entity.Todo;
import com.example.todo.persistence.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public List<Todo> update(final Todo todo) {
        validate(todo);

        Optional<Todo> byId = todoRepository.findById(todo.getId());
        byId.ifPresent(o -> {
            o.setTitle(todo.getTitle());
            o.setDone(todo.isDone());
            todoRepository.save(o);
        });

        return retrieve(todo.getUserId());
    }

    public List<Todo> delete(final Todo todo) {
        validate(todo);
        try {
            todoRepository.delete(todo);
        } catch (Exception e) {
            log.error("error deleting entity ", todo.getId(), e);
            throw new RuntimeException("error deleting entity " + todo.getId());
        }
        return retrieve(todo.getId());
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
