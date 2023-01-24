package com.example.rickandmorty.dto.external.mapper;

import com.example.rickandmorty.dto.external.dto.ApiCharacterDto;
import com.example.rickandmorty.model.Gender;
import com.example.rickandmorty.model.MovieCharacter;
import com.example.rickandmorty.model.Status;
import org.springframework.stereotype.Component;

@Component
public class MovieCharacterParser implements
        ApiParser<MovieCharacter, ApiCharacterDto> {
    @Override
    public MovieCharacter parseToObject(ApiCharacterDto dto) {
        MovieCharacter movieCharacter = new MovieCharacter();
        movieCharacter.setExternalId(dto.getId());
        movieCharacter.setName(dto.getName());
        movieCharacter.setStatus(Status.valueOf(dto.getStatus().toUpperCase()));
        movieCharacter.setGender(Gender.valueOf(dto.getGender().toUpperCase()));
        return movieCharacter;
    }
}
