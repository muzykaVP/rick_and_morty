package com.example.rick_and_morty.dto.mapper;

public interface ResponseDtoMapper<T, D> {
    D mapToDto(T object);
}
