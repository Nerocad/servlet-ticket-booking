package com.walking.tbooking.model;

import java.time.LocalDate;
import java.util.List;

public class Passenger {
    private Long id;
    private String fullName;
    private Gender gender;
    private LocalDate birthDate;
    private String passportData;
    private List<FavoriteAirports> favoriteAirports;


    public Passenger() {
    }

    public Passenger(Long id, String fullName,
                     Gender gender, LocalDate birthDate,
                     String passportData, List<FavoriteAirports> favoriteAirports) {
        this.id = id;
        this.fullName = fullName;
        this.gender = gender;
        this.birthDate = birthDate;
        this.passportData = passportData;
        this.favoriteAirports = favoriteAirports;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getPassportData() {
        return passportData;
    }

    public void setPassportData(String passportData) {
        this.passportData = passportData;
    }

    public List<FavoriteAirports> getFavoriteAirports() {
        return favoriteAirports;
    }

    public void setFavoriteAirports(List<FavoriteAirports> favoriteAirports) {
        this.favoriteAirports = favoriteAirports;
    }

    public enum Gender {
        MALE,FEMALE
    }
}

