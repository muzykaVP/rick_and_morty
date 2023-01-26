package com.example.rickandmorty.controller;

import com.example.rickandmorty.dto.mapper.ResponseDtoMapper;
import com.example.rickandmorty.dto.response.MovieCharacterResponseDto;
import com.example.rickandmorty.model.MovieCharacter;
import com.example.rickandmorty.service.MovieCharacterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movie-characters")
public class MovieCharacterController {
    private final MovieCharacterService characterService;
    private final ResponseDtoMapper<MovieCharacter, MovieCharacterResponseDto> responseMapper;

    public MovieCharacterController(
            MovieCharacterService characterService,
            ResponseDtoMapper<MovieCharacter, MovieCharacterResponseDto> responseMapper) {
        this.characterService = characterService;
        this.responseMapper = responseMapper;
    }

    @GetMapping("/random")
    @Operation(description = "returns random character from db")
    public MovieCharacterResponseDto getRandomCharacter() {
        MovieCharacter randomCharacter = characterService.getRandomCharacter();
        return responseMapper.mapToDto(randomCharacter);
    }

    @GetMapping("/by-name")
    @Operation(description = "returns list of characters that contains given name part")
    public List<MovieCharacterResponseDto> getByNameContains(
            @Parameter(description = "part of a name for searching")
            @RequestParam("name") String namePart) {
        List<MovieCharacter> byNameContains = characterService.findByNameContains(namePart);
        return byNameContains.stream()
                .map(responseMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
