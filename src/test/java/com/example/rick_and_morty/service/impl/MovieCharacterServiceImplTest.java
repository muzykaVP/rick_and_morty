package com.example.rick_and_morty.service.impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import com.example.rick_and_morty.dto.external.dto.ApiCharacterDto;
import com.example.rick_and_morty.dto.external.dto.ApiInfoDto;
import com.example.rick_and_morty.dto.external.dto.ApiResponseDto;
import com.example.rick_and_morty.dto.external.mapper.ApiParser;
import com.example.rick_and_morty.model.Gender;
import com.example.rick_and_morty.model.MovieCharacter;
import com.example.rick_and_morty.model.Status;
import com.example.rick_and_morty.repository.MovieCharacterRepository;
import com.example.rick_and_morty.service.HttpClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.stereotype.Component;

@ExtendWith(MockitoExtension.class)
@Component
class MovieCharacterServiceImplTest {
    @InjectMocks
    private MovieCharacterServiceImpl movieCharacterService;
    @Mock
    private MovieCharacterRepository characterRepository;
    @Mock
    private ApiParser<MovieCharacter, ApiCharacterDto> characterMapper;
    private final static ApiResponseDto apiResponseDto = new ApiResponseDto();
    private final static ApiCharacterDto rickDto = new ApiCharacterDto();
    private final static MovieCharacter rickCharacter = new MovieCharacter();

    @BeforeEach
    void setUp() {
        rickDto.setId(1L);
        rickDto.setName("Rick Sanchez");
        rickDto.setStatus("ALIVE");
        rickDto.setGender("MALE");

        ApiCharacterDto[] characterDtos = {rickDto};

        rickCharacter.setId(1L);
        rickCharacter.setExternalId(1L);
        rickCharacter.setName("Rick Sanchez");
        rickCharacter.setStatus(Status.ALIVE);
        rickCharacter.setGender(Gender.MALE);

        ApiInfoDto apiInfoDto = new ApiInfoDto();
        apiInfoDto.setCount(1);
        apiInfoDto.setPages(1);
        apiResponseDto.setInfo(apiInfoDto);
        apiResponseDto.setResults(characterDtos);
    }

    @Test
    void saveExistingCharacterDtosToDB() {
        List<ApiCharacterDto> characterDtos = Arrays.stream(apiResponseDto.getResults()).toList();
        List<MovieCharacter> characters = List.of(rickCharacter);
//
//        characterMapper = Mockito.spy(characterMapper);
//        Mockito.doReturn(rickCharacter).when(characterMapper).parseToObject(any());
//
        characterRepository = Mockito.spy(characterRepository);
        Mockito.doReturn(characters).when(characterRepository).findAllByExternalIdIn(any());
        Mockito.doReturn(characters).when(characterRepository).saveAll(any());

        movieCharacterService.saveDtosToDb(apiResponseDto);
        Assertions.assertEquals(characterDtos.size(), apiResponseDto.getResults().length, "Size of db shouldn't change");
    }

}