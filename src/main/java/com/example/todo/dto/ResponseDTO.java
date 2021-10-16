package com.example.todo.dto;

import lombok.*;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ResponseDTO <T>{
    private String error;
    private List<T> data;
}
