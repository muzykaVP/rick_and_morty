package com.example.rickandmorty.dto.external.mapper;

public interface ApiMapper<T, D> {
    T parseToObject(D dto);
}
