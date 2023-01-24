package com.example.rickandmorty.dto.external.mapper;

public interface ApiParser<T, D> {
    T parseToObject(D dto);
}
