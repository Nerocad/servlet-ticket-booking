package com.walking.tbooking.model;

public class FavoriteAirports {
    private Long id;
    private String airportCode;
    private String airportName;

    public FavoriteAirports(Long id, String airportCode, String airportName) {
        this.id = id;
        this.airportCode = airportCode;
        this.airportName = airportName;
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

    public String getAirportName() {
        return airportName;
    }

    public void setAirportName(String airportName) {
        this.airportName = airportName;
    }
}
