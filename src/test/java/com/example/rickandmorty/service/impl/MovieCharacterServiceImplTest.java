package com.example.rickandmorty.service.impl;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import com.example.rickandmorty.model.MovieCharacter;
import com.example.rickandmorty.repository.MovieCharacterRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MovieCharacterServiceImplTest {
    @InjectMocks
    private MovieCharacterServiceImpl movieCharacterService;
    @Mock
    private MovieCharacterRepository characterRepository;

    private final static MovieCharacter rickCharacter = new MovieCharacter();
    private final static MovieCharacter mortyCharacter = new MovieCharacter();

    @BeforeAll
    static void setUp() {
        rickCharacter.setId(1L);
        rickCharacter.setExternalId(1L);
        rickCharacter.setName("Rick Sanchez");
        rickCharacter.setStatus(MovieCharacter.Status.ALIVE);
        rickCharacter.setGender(MovieCharacter.Gender.MALE);

        mortyCharacter.setId(2L);
        mortyCharacter.setExternalId(2L);
        mortyCharacter.setName("Morty Smith");
        mortyCharacter.setStatus(MovieCharacter.Status.ALIVE);
        mortyCharacter.setGender(MovieCharacter.Gender.MALE);
    }

    @Test
    void removeExistingCharactersByExternalIdWithOneOfTwoExist() {
        Set<Long> externalIds = new HashSet<>();
        externalIds.add(rickCharacter.getExternalId());
        externalIds.add(mortyCharacter.getExternalId());
        int expectedIdSetSize = 1;
        List<MovieCharacter> characters = List.of(rickCharacter);
        Mockito.doReturn(characters).when(characterRepository).findAllByExternalIdIn(any());
        int actualIdsSetSize = movieCharacterService.removeExistingCharactersByExternalId(externalIds).size();
        Assertions.assertEquals(expectedIdSetSize, actualIdsSetSize, "Size of id set must be 1");
    }

    @Test
    void removeExistingCharactersByExternalIdWithEmptyDB() {
        Set<Long> externalIds = new HashSet<>();
        externalIds.add(rickCharacter.getExternalId());
        externalIds.add(mortyCharacter.getExternalId());
        int expectedIdSetSize = 2;
        Mockito.doReturn(Collections.emptyList()).when(characterRepository).findAllByExternalIdIn(any());
        int actualIdsSetSize = movieCharacterService.removeExistingCharactersByExternalId(externalIds).size();
        Assertions.assertEquals(expectedIdSetSize, actualIdsSetSize, "Size of id set must be 2");
    }
}