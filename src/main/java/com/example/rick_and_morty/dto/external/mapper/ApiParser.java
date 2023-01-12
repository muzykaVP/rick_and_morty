package com.example.rick_and_morty.dto.external.mapper;

public interface ApiParser<T, D> {
    T parseToObject(D dto);
}
