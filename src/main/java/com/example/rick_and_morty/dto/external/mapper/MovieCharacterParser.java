package com.example.rick_and_morty.dto.external.mapper;

import com.example.rick_and_morty.dto.external.dto.ApiCharacterDto;
import com.example.rick_and_morty.model.Gender;
import com.example.rick_and_morty.model.MovieCharacter;
import com.example.rick_and_morty.model.Status;
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
