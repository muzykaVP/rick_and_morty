package com.example.rickandmorty.service.impl;

import com.example.rickandmorty.dto.external.dto.ApiCharacterDto;
import com.example.rickandmorty.dto.external.dto.ApiResponseDto;
import com.example.rickandmorty.dto.external.mapper.ApiMapper;
import com.example.rickandmorty.model.MovieCharacter;
import com.example.rickandmorty.repository.MovieCharacterRepository;
import com.example.rickandmorty.service.HttpClient;
import com.example.rickandmorty.service.MovieCharacterService;
import jakarta.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class MovieCharacterServiceImpl implements MovieCharacterService {
    private final HttpClient httpClient;
    private final MovieCharacterRepository characterRepository;
    private final ApiMapper<MovieCharacter, ApiCharacterDto> characterMapper;

    public MovieCharacterServiceImpl(
            HttpClient httpClient,
            MovieCharacterRepository characterRepository,
            ApiMapper<MovieCharacter, ApiCharacterDto> characterMapper) {
        this.httpClient = httpClient;
        this.characterRepository = characterRepository;
        this.characterMapper = characterMapper;
    }

    @Override
    @PostConstruct
    @Scheduled(cron = "*/20 * * * * ?")
    public void syncExternalCharacters() {
        log.info("syncExternalCharacters was invoked at " + LocalDateTime.now());
        ApiResponseDto apiResponseDto =
                httpClient.get("https://rickandmortyapi.com/api/character", ApiResponseDto.class);
        saveDtosToDb(apiResponseDto);
        updateExistingCharacters(apiResponseDto);
        while (apiResponseDto.getInfo().getNext() != null) {
            apiResponseDto =
                    httpClient.get(apiResponseDto.getInfo().getNext(), ApiResponseDto.class);
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
                () -> new RuntimeException("Can't get random character"));
    }

    private void saveDtosToDb(ApiResponseDto apiResponseDto) {
        Map<Long, ApiCharacterDto> externalDtos = Arrays.stream(apiResponseDto.getResults())
                .collect(Collectors.toMap(ApiCharacterDto::getId, Function.identity()));

        Set<Long> externalIds = externalDtos.keySet();

        Set<Long> characterIdsToSave = removeExistingCharactersByExternalId(externalIds);

        List<MovieCharacter> charactersToSave = characterIdsToSave.stream()
                .map(i -> characterMapper.parseToObject(externalDtos.get(i))).toList();

        characterRepository.saveAll(charactersToSave);
    }

    Set<Long> removeExistingCharactersByExternalId(Set<Long> externalIds) {
        List<MovieCharacter> existingCharacters =
                characterRepository.findAllByExternalIdIn(externalIds);

        Map<Long, MovieCharacter> existingCharactersWithId = existingCharacters.stream()
                .collect(Collectors.toMap(MovieCharacter::getExternalId, Function.identity()));

        Set<Long> existingIds = existingCharactersWithId.keySet();

        externalIds.removeAll(existingIds);
        return externalIds;
    }

    private void updateExistingCharacters(ApiResponseDto apiResponseDto) {
        Map<Long, ApiCharacterDto> externalDtos = Arrays.stream(apiResponseDto.getResults())
                .collect(Collectors.toMap(ApiCharacterDto::getId, Function.identity()));

        Set<Long> externalIds = externalDtos.keySet();

        List<MovieCharacter> existingCharacters =
                characterRepository.findAllByExternalIdIn(externalIds);

        characterRepository.saveAll(existingCharacters);
    }
}
