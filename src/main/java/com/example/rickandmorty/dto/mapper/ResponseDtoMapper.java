package com.example.rickandmorty.dto.mapper;

public interface ResponseDtoMapper<T, D> {
    D mapToDto(T object);
}
