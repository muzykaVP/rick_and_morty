package com.example.rickandmorty.dto.external.dto;

import lombok.Data;

@Data
public class ApiCharacterDto {
    private Long id;
    private String name;
    private String gender;
    private String status;
}
