package com.example.rickandmorty.dto.response;

import com.example.rickandmorty.model.Gender;
import com.example.rickandmorty.model.Status;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieCharacterResponseDto {
    private Long id;
    private String name;
    private Status status;
    private Gender gender;
}
