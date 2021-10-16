package com.example.todo.dto;

import com.example.todo.entity.Todo;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class RequestDTO {

    private String id;
    private String title;
    private boolean done;

    public RequestDTO(Todo todo) {
        this.id = todo.getId();
        this.title = todo.getTitle();
        this.done = todo.isDone();
    }

    public static Todo toEntity(final RequestDTO dto) {
        return Todo.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .done(dto.isDone())
                .build();
    }

}
