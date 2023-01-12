package com.example.rick_and_morty.service.impl;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import com.example.rick_and_morty.dto.external.dto.ApiCharacterDto;
import com.example.rick_and_morty.dto.external.dto.ApiResponseDto;
import com.example.rick_and_morty.dto.external.mapper.ApiParser;
import com.example.rick_and_morty.model.MovieCharacter;
import com.example.rick_and_morty.repository.MovieCharacterRepository;
import com.example.rick_and_morty.service.HttpClient;
import com.example.rick_and_morty.service.MovieCharacterService;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

@Service
@Log4j2
public class MovieCharacterServiceImpl implements MovieCharacterService {
    private final HttpClient httpClient;
    private final MovieCharacterRepository characterRepository;
    private final ApiParser<MovieCharacter, ApiCharacterDto> characterMapper;

    public MovieCharacterServiceImpl(
            HttpClient httpClient,
            MovieCharacterRepository characterRepository,
            ApiParser<MovieCharacter, ApiCharacterDto> characterMapper) {
        this.httpClient = httpClient;
        this.characterRepository = characterRepository;
        this.characterMapper = characterMapper;
    }

    @Override
    @PostMapping
    @Scheduled(cron = "*/20 * * * * ?")
    public void syncExternalCharacters() {
        log.info("syncExternalCharacters was invoked at "+ LocalDateTime.now());
        ApiResponseDto apiResponseDto =
                httpClient.get("https://rickandmortyapi.com/api/character", ApiResponseDto.class);
        saveDtosToDb(apiResponseDto);
        updateExistingCharacters(apiResponseDto);
        while (apiResponseDto.getInfo().getNext() != null) {
            apiResponseDto = httpClient.get(apiResponseDto.getInfo().getNext(), ApiResponseDto.class);
            saveDtosToDb(apiResponseDto);
            updateExistingCharacters(apiResponseDto);
        }
    }

    @Override
    public List<MovieCharacter> findByNameContains(String namePart) {
        return characterRepository.findByNameContains(namePart);
    }

    @Override
    public MovieCharacter getRandomCharacter() {
        long characterCounter = characterRepository.count();
        long randomId = (long) (Math.random() * characterCounter);
        return characterRepository.findById(randomId).orElseThrow(
                ()-> new RuntimeException("Can't get random character"));
    }

    void saveDtosToDb(ApiResponseDto apiResponseDto) {
        Map<Long, ApiCharacterDto> externalDtos = Arrays.stream(apiResponseDto.getResults())
                .collect(Collectors.toMap(ApiCharacterDto::getId, Function.identity()));

        Set<Long> externalIds = externalDtos.keySet();

        List<MovieCharacter> existingCharacters = characterRepository.findAllByExternalIdIn(externalIds);

        Map<Long, MovieCharacter> existingCharactersWithId = existingCharacters.stream()
                .collect(Collectors.toMap(MovieCharacter::getExternalId, Function.identity()));

        Set<Long> existingIds = existingCharactersWithId.keySet();

        externalIds.removeAll(existingIds);

        List<MovieCharacter> charactersToSave = externalIds.stream()
                .map(i -> characterMapper.parseToObject(externalDtos.get(i))).toList();

        characterRepository.saveAll(charactersToSave);
    }

    private void updateExistingCharacters(ApiResponseDto apiResponseDto) {
        Map<Long, ApiCharacterDto> externalDtos = Arrays.stream(apiResponseDto.getResults())
                .collect(Collectors.toMap(ApiCharacterDto::getId, Function.identity()));

        Set<Long> externalIds = externalDtos.keySet();

        List<MovieCharacter> existingCharacters = characterRepository.findAllByExternalIdIn(externalIds);

        characterRepository.saveAll(existingCharacters);
    }
}
