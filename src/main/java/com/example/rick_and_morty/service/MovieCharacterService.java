package com.example.rick_and_morty.service;

import java.util.List;
import com.example.rick_and_morty.model.MovieCharacter;

public interface MovieCharacterService {
    void syncExternalCharacters();
    List<MovieCharacter> findByNameContains(String namePart);
    MovieCharacter getRandomCharacter();
}
