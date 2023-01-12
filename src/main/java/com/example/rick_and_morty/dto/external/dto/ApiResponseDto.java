package com.example.rick_and_morty.dto.external.dto;

import lombok.Data;

@Data
public class ApiResponseDto {
    private ApiInfoDto info;
    private ApiCharacterDto[] results;
}
