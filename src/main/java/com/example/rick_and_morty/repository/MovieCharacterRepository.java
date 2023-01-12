package com.example.rick_and_morty.repository;

import java.util.List;
import java.util.Set;
import com.example.rick_and_morty.model.MovieCharacter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieCharacterRepository extends JpaRepository<MovieCharacter, Long> {
    List<MovieCharacter> findAllByExternalIdIn(Set<Long> externalIds);
    List<MovieCharacter> findByNameContains(String namePart);
}
