package com.example.rick_and_morty.model;

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