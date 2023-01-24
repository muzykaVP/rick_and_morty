package com.example.rickandmorty.service.impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import com.example.rickandmorty.dto.external.dto.ApiCharacterDto;
import com.example.rickandmorty.dto.external.dto.ApiInfoDto;
import com.example.rickandmorty.dto.external.dto.ApiResponseDto;
import com.example.rickandmorty.dto.external.mapper.ApiParser;
import com.example.rickandmorty.model.Gender;
import com.example.rickandmorty.model.MovieCharacter;
import com.example.rickandmorty.model.Status;
import com.example.rickandmorty.repository.MovieCharacterRepository;
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
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

@ExtendWith(MockitoExtension.class)
//@DataJpaTest
//@Testcontainers
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MovieCharacterServiceImplTest {
    @InjectMocks
    private MovieCharacterServiceImpl movieCharacterService;
    @Mock
    private MovieCharacterRepository characterRepository;
//    @Container
//    public static PostgreSQLContainer<?> postgreSQLContainer =
//            new PostgreSQLContainer<>("postgres:13")
//                    .withDatabaseName("rick_and_morty")
//                    .withUsername("postgres")
//                    .withPassword("12345");
//
//    @DynamicPropertySource
//    static void registerPgProperties(DynamicPropertyRegistry registry) {
//        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
//        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
//        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
//    }

    private final static MovieCharacter rickCharacter = new MovieCharacter();
    private final static MovieCharacter mortyCharacter = new MovieCharacter();

    @BeforeAll
    static void setUp() {
        rickCharacter.setId(1L);
        rickCharacter.setExternalId(1L);
        rickCharacter.setName("Rick Sanchez");
        rickCharacter.setStatus(Status.ALIVE);
        rickCharacter.setGender(Gender.MALE);

        mortyCharacter.setId(2L);
        mortyCharacter.setExternalId(2L);
        mortyCharacter.setName("Morty Smith");
        mortyCharacter.setStatus(Status.ALIVE);
        mortyCharacter.setGender(Gender.MALE);
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