package com.example.rickandmorty.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "movie_characters")
public class MovieCharacter {
    @Id
    @GeneratedValue(generator = "movie_character_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "movie_character_id_seq", sequenceName = "movie_character_id_seq",
            allocationSize = 1)
    private Long id;
    private Long externalId;
    private String name;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Enumerated(EnumType.STRING)
    private Gender gender;

    public enum Status {
        ALIVE("Alive"),
        DEAD("Dead"),
        UNKNOWN("unknown");
        private String status;

        Status(String status) {
            this.status = status;
        }
    }

    public enum Gender {
        MALE("Male"),
        FEMALE("Female"),
        GENDERLESS("Genderless"),
        UNKNOWN("unknown");
        private String gender;

        Gender(String gender) {
            this.gender = gender;
        }
    }
}
