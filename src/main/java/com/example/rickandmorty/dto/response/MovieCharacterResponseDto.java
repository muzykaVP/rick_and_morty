package com.example.rickandmorty.dto.response;

import com.example.rickandmorty.model.MovieCharacter;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieCharacterResponseDto {
    private Long id;
    private String name;
    private MovieCharacter.Status status;
    private MovieCharacter.Gender gender;
}
