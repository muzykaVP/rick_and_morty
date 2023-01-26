package com.example.rickandmorty.dto.external.mapper;

import com.example.rickandmorty.dto.external.dto.ApiCharacterDto;
import com.example.rickandmorty.model.MovieCharacter;
import org.springframework.stereotype.Component;

@Component
public class MovieCharacterApiMapper implements
        ApiMapper<MovieCharacter, ApiCharacterDto> {
    @Override
    public MovieCharacter parseToObject(ApiCharacterDto dto) {
        MovieCharacter movieCharacter = new MovieCharacter();
        movieCharacter.setExternalId(dto.getId());
        movieCharacter.setName(dto.getName());
        movieCharacter.setStatus(MovieCharacter.Status.valueOf(dto.getStatus().toUpperCase()));
        movieCharacter.setGender(MovieCharacter.Gender.valueOf(dto.getGender().toUpperCase()));
        return movieCharacter;
    }
}
