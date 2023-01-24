package com.example.rickandmorty.service;

import com.example.rickandmorty.model.MovieCharacter;
import java.util.List;

public interface MovieCharacterService {
    void syncExternalCharacters();

    List<MovieCharacter> findByNameContains(String namePart);

    MovieCharacter getRandomCharacter();
}
