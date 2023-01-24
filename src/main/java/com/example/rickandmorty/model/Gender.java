package com.example.rickandmorty.model;

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
