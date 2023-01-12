package com.example.rick_and_morty.controller;

import java.util.List;
import java.util.stream.Collectors;
import com.example.rick_and_morty.dto.mapper.ResponseDtoMapper;
import com.example.rick_and_morty.dto.response.MovieCharacterResponseDto;
import com.example.rick_and_morty.model.MovieCharacter;
import com.example.rick_and_morty.service.MovieCharacterService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
    @ApiOperation(value = "returns random character from db")
    public MovieCharacterResponseDto getRandomCharacter() {
        MovieCharacter randomCharacter = characterService.getRandomCharacter();
        return responseMapper.mapToDto(randomCharacter);
    }

    @GetMapping("/by-name")
    @ApiOperation(value = "returns list of characters that contains given name part")
    public List<MovieCharacterResponseDto> getByNameContains(
            @ApiParam(value = "part of a name for searching")
            @RequestParam("name") String namePart) {
        List<MovieCharacter> byNameContains = characterService.findByNameContains(namePart);
       return byNameContains.stream()
               .map(responseMapper::mapToDto)
               .collect(Collectors.toList());
    }
}
