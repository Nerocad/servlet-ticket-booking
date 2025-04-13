package com.walking.tbooking.converter.db;

import com.walking.tbooking.model.Flight;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;

public class FlightConverter implements ResultSetConverter<Optional<Flight>> {

    @Override
    public Optional<Flight> convert(ResultSet rs) throws SQLException {
        return rs.next() ? Optional.of(mapRow(rs)) : Optional.empty();
    }

    private Flight mapRow(ResultSet rs) throws SQLException {
        Flight flight = new Flight();

        flight.setId(rs.getLong("id"));
        flight.setDepartureTime(rs.getObject("departure_time", LocalDateTime.class));
        flight.setArrivalTime(rs.getObject("arrival_time", LocalDateTime.class));
        flight.setDepartureAirportCode(rs.getString("departure_airport_code"));
        flight.setArrivalAirportCode(rs.getString("arrival_airport_code"));
        flight.setTotalSeats(rs.getInt("total_seats"));
        flight.setAvailableSeats(rs.getInt("available_seats"));

        return flight;
    }
}
