package com.example.rick_and_morty.dto.response;

import com.example.rick_and_morty.model.Gender;
import com.example.rick_and_morty.model.Status;
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
