package com.example.todo.controller;

import com.example.todo.dto.RequestDTO;
import com.example.todo.dto.ResponseDTO;
import com.example.todo.entity.Todo;
import com.example.todo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/todos")
@RestController
public class TodoController {

    @Autowired
    private TodoService todoService;

    @PostMapping
    public ResponseEntity<?> createTodo(@RequestBody RequestDTO requestDTO,
                                        @AuthenticationPrincipal String userId) {
        try {
//            String temporaryUserId = "temporary-user";
            Todo todo = RequestDTO.toEntity(requestDTO);
            todo.setId(null);
            todo.setUserId(userId);

            List<Todo> todos = todoService.create(todo);

            List<RequestDTO> dtos = todos.stream().map(RequestDTO::new).collect(Collectors.toList());
            ResponseDTO<RequestDTO> response = ResponseDTO.<RequestDTO>builder()
                    .data(dtos)
                    .build();

            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            String message = e.getMessage();
            ResponseDTO<RequestDTO> response = ResponseDTO.<RequestDTO>builder()
                    .error(message)
                    .build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping
    public ResponseEntity<?> retrieveTodoList(@AuthenticationPrincipal String userId) {
//        String temporaryUserId = "temporary-user";

        List<Todo> todos = todoService.retrieve(userId);

        List<RequestDTO> requestDTOS = todos.stream().map(RequestDTO::new).collect(Collectors.toList());

        ResponseDTO<RequestDTO> response = ResponseDTO.<RequestDTO>builder()
                .data(requestDTOS)
                .build();

        return ResponseEntity.ok().body(response);
    }

    @PutMapping
    public ResponseEntity<?> updateTodo(@RequestBody RequestDTO requestDTO,
                                        @AuthenticationPrincipal String userId) {
//        String temporaryUserId = "temporary-user";
        Todo todo = RequestDTO.toEntity(requestDTO);
        todo.setUserId(userId);
        List<Todo> update = todoService.update(todo);
        List<RequestDTO> dtos = update.stream().map(RequestDTO::new).collect(Collectors.toList());

        ResponseDTO<RequestDTO> response = ResponseDTO.<RequestDTO>builder()
                .data(dtos)
                .build();

        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteTodo(@RequestBody RequestDTO requestDTO,
                                        @AuthenticationPrincipal String userId) {
        try {
//            String temporaryUserId = "temporary-user";

            Todo todo = RequestDTO.toEntity(requestDTO);
            todo.setUserId(userId);

            List<Todo> deletedList = todoService.delete(todo);
            List<RequestDTO> dtos = deletedList.stream().map(RequestDTO::new).collect(Collectors.toList());
            ResponseDTO<RequestDTO> response = ResponseDTO.<RequestDTO>builder().data(dtos).build();

            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            String message = e.getMessage();
            ResponseDTO<RequestDTO> response = ResponseDTO.<RequestDTO>builder().error(message).build();

            return ResponseEntity.ok().body(response);
        }
    }

}
