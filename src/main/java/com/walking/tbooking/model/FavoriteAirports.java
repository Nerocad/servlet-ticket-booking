package com.walking.tbooking.model;

public class FavoriteAirports {
    private Long id;
    private String airportCode;

    public FavoriteAirports(Long id, String airportCode) {
        this.id = id;
        this.airportCode = airportCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAirportCode() {
        return airportCode;
    }

    public void setAirportCode(String airportCode) {
        this.airportCode = airportCode;
    }
}
