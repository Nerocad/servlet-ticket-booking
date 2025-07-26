package com.walking.tbooking.repository;

import com.walking.tbooking.converter.db.FlightConverter;
import com.walking.tbooking.model.Flight;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class FlightRepository {

    private final DataSource dataSource;
    private final FlightConverter converter;

    public FlightRepository(DataSource dataSource, FlightConverter converter) {
        this.dataSource = dataSource;
        this.converter = converter;
    }

    public Optional<Flight> findById(Long id) {
        String sql = "select * from flight where id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            return converter.convert(rs);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при поиске рейса");
        }
    }

    public Flight create(Flight flight) {
        String sql = """
                insert into (departure_time, arrival_time, departure_airport_code,
                            arrival_airport_code, total_seats, available_seats)
                            values (?, ?, ?, ?, ?, ?) returning id
                """;

        try (Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {

            setParameters(flight, statement);
            ResultSet rs = statement.executeQuery();
            rs.next();
            flight.setId(rs.getLong(1));

            return flight;
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при создании рейса");
        }
    }

    public Flight update(Flight flight) {
        String sql = """
                update flight
                    set departure_time = ?,
                        arrival_time = ?,
                        departure_airport_code = ?,
                        arrival_airport_code = ?,
                        total_seats = ?,
                        available_seats = ?
                    where id = ?
                """;
        try (Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {

            setParameters(flight, statement);
            statement.setLong(7, flight.getId());
            statement.executeUpdate();

            return flight;
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при обновлении рейса");
        }
    }

    public void deleteById(Long id) {
        String sql = "delete from flight where id = ?";

        try (Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при удалении рейса");
        }
    }

    private void setParameters(Flight flight, PreparedStatement statement) throws SQLException {
        statement.setTimestamp(1, java.sql.Timestamp.valueOf(flight.getDepartureTime()));
        statement.setTimestamp(2, java.sql.Timestamp.valueOf(flight.getArrivalTime()));
        statement.setString(3, flight.getDepartureAirportCode());
        statement.setString(4, flight.getArrivalAirportCode());
        statement.setInt(5, flight.getTotalSeats());
        statement.setInt(6, flight.getAvailableSeats());
    }
}
