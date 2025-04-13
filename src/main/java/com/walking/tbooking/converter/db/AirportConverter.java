package com.walking.tbooking.converter.db;

import com.walking.tbooking.model.Airport;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class AirportConverter implements ResultSetConverter<Optional<Airport>> {

    @Override
    public Optional<Airport> convert(ResultSet rs) throws SQLException {
        return rs.next() ? Optional.of(mapRow(rs)) : Optional.empty();
    }

    private Airport mapRow(ResultSet rs) throws SQLException {
        Airport airport = new Airport();

        airport.setId(rs.getLong("id"));
        airport.setName(rs.getString("name"));
        airport.setCode(rs.getString("code"));
        airport.setAddress(rs.getString("address"));

        return airport;
    }


}
