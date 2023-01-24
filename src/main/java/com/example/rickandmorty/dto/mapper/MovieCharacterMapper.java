package com.example.rickandmorty.dto.mapper;

import com.example.rickandmorty.dto.response.MovieCharacterResponseDto;
import com.example.rickandmorty.model.MovieCharacter;
import org.springframework.stereotype.Component;

@Component
public class MovieCharacterMapper implements
        ResponseDtoMapper<MovieCharacter, MovieCharacterResponseDto> {
    @Override
    public MovieCharacterResponseDto mapToDto(MovieCharacter object) {
        MovieCharacterResponseDto responseDto = new MovieCharacterResponseDto();
        responseDto.setId(object.getId());
        responseDto.setName(object.getName());
        responseDto.setStatus(object.getStatus());
        responseDto.setGender(object.getGender());
        return responseDto;
    }
}
