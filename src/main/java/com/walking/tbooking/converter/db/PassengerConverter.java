package com.walking.tbooking.converter.db;

import com.walking.tbooking.model.FavoriteAirports;
import com.walking.tbooking.model.Gender;
import com.walking.tbooking.model.Passenger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PassengerConverter implements ResultSetConverter<Optional<Passenger>>{

    @Override
    public Optional<Passenger> convert(ResultSet rs) throws SQLException {
        Passenger passenger = null;
        List<FavoriteAirports> favoriteAirports = new ArrayList<>();

        while(rs.next()) {
            if (passenger == null) {
                passenger = mapRow(rs);
            }

            if (rs.getObject("airport_id") != null) {
                favoriteAirports.add(mapFavoriteAirport(rs));
            }
        }

        if (passenger != null) {
            passenger.setFavoriteAirports(favoriteAirports);
            return Optional.of(passenger);
        }
        return Optional.empty();
    }

    private Passenger mapRow(ResultSet rs) throws SQLException {
        Passenger passenger = new Passenger();
        passenger.setId(rs.getLong("passenger_id"));
        passenger.setFullName(rs.getString("passenger_name"));
        passenger.setGender(Gender.valueOf(
                rs.getString("passenger_gender")
        ));
        passenger.setBirthDate(rs.getDate("passenger_birth_date").toLocalDate());
        passenger.setPassportData(rs.getString("passenger_passport"));

        return passenger;
    }

    private FavoriteAirports mapFavoriteAirport(ResultSet rs) throws SQLException {
        return new FavoriteAirports(
                rs.getLong("airport_id"),
                rs.getString("airport_code"),
                rs.getString("airport_name")
        );
    }
}
