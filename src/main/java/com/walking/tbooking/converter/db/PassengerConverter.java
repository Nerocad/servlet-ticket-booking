package com.walking.tbooking.converter.db;

import com.walking.tbooking.model.FavoriteAirports;
import com.walking.tbooking.model.Passenger;
import com.walking.tbooking.repository.PassengerRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PassengerConverter implements ResultSetPassengerConverter<Optional<Passenger>>{

    PassengerRepository passengerRepository;
    List<FavoriteAirports> airports = new ArrayList<>();


    public PassengerConverter(PassengerRepository passengerRepository) {
        this.passengerRepository = passengerRepository;
    }


    @Override
    public Optional<Passenger> convert(ResultSet rs, Long passenger_id) throws SQLException {
        return rs.next() ? Optional.of(mapRow(rs, passenger_id)) : Optional.empty();
    }

    private Passenger mapRow(ResultSet rs, Long passenger_id) throws SQLException {

        Passenger passenger = new Passenger();

        passenger.setId(rs.getLong("id"));
        passenger.setFullName(rs.getString("fullname"));
        passenger.setGender(Passenger.Gender.valueOf(rs.getString("Gender")));
        passenger.setBirthDate(rs.getObject("birth_date", LocalDate.class));
        passenger.setPassportData(rs.getString("passport_data"));
        airports = passengerRepository.getFavoriteAirportsByPassengerId(passenger_id);
        passenger.setFavoriteAirports(airports);

        return passenger;

    }
}
