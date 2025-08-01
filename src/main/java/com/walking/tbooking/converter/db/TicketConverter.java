package com.walking.tbooking.converter.db;

import com.walking.tbooking.model.ServiceClass;
import com.walking.tbooking.model.Ticket;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class TicketConverter implements ResultSetConverter<Optional<Ticket>> {

    @Override
    public Optional<Ticket> convert(ResultSet rs) throws SQLException {
        return rs.next() ? Optional.of(mapRow(rs)) : Optional.empty();
    }

    private Ticket mapRow(ResultSet rs) throws SQLException {
        Ticket ticket = new Ticket();

        ticket.setId(rs.getLong("id"));
        ticket.setServiceClass(ServiceClass.valueOf(rs.getString("service_class ")));
        ticket.setSeatNumber(rs.getInt("seat_number"));
        ticket.setBaggageAllowance(rs.getString("baggage_allowance"));
        ticket.setFlightId(rs.getLong("flight_id"));
        ticket.setPassengerId(rs.getLong("passenger_id"));

        return ticket;
    }
}
