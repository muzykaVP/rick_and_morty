package com.example.rick_and_morty.dto.mapper;

import com.example.rick_and_morty.dto.response.MovieCharacterResponseDto;
import com.example.rick_and_morty.model.MovieCharacter;
import org.springframework.stereotype.Component;

@Component
public class MovieCharacterMapper implements ResponseDtoMapper<MovieCharacter, MovieCharacterResponseDto>{
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
