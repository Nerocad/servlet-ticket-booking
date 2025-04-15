package com.walking.tbooking.repository;

import com.walking.tbooking.converter.db.FlightConverter;
import com.walking.tbooking.model.Flight;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


public class FlightRepositoryTest {

    private DataSource dataSource;
    private Connection connection;
    private PreparedStatement statement;
    private ResultSet rs;
    private FlightConverter converter;
    private FlightRepository flightRepository;

    @BeforeEach
    void setUp() throws SQLException {
        dataSource = mock(HikariDataSource.class);
        connection = mock(Connection.class);
        statement = mock(PreparedStatement.class);
        rs = mock(ResultSet.class);
        converter = mock(FlightConverter.class);

        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(statement);

        flightRepository = new FlightRepository(dataSource, converter);
    }

    @Test
    void findById_success() throws SQLException {
        Long id = 1L;
        LocalDateTime depTime = LocalDateTime.of(2025, 4, 2, 12, 50, 0);
        LocalDateTime arrTime = LocalDateTime.of(2025, 4, 2, 16, 50, 0);

        Flight expectedFlight = new Flight(id, depTime, arrTime, "SVO", "DME", 200, 20);

        when(statement.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        when(converter.convert(rs)).thenReturn(Optional.of(expectedFlight));

        Optional<Flight> result = flightRepository.findById(id);

        assertTrue(result.isPresent());
        assertEquals(expectedFlight, result.get());
        verify(statement).setLong(1, id);
    }

    @Test
    void findById_not_found() throws SQLException {
        when(statement.executeQuery()).thenReturn(rs);
        when(converter.convert(rs)).thenReturn(Optional.empty());

        Optional<Flight> result = flightRepository.findById(1L);

        assertTrue(result.isEmpty());
    }

    @Test
    void flight_update() throws SQLException {
        LocalDateTime depTime = LocalDateTime.of(2025, 4, 2, 12, 50, 0);
        LocalDateTime arrTime = LocalDateTime.of(2025, 4, 2, 16, 50, 0);
        Flight expectedFlight = new Flight(1L, depTime, arrTime, "SVO", "DME", 200, 20);

        when(connection.prepareStatement(anyString())).thenReturn(statement);
        flightRepository.update(expectedFlight);
        verify(statement).setTimestamp(1, java.sql.Timestamp.valueOf(depTime));
        verify(statement).setTimestamp(2, java.sql.Timestamp.valueOf(arrTime));
        verify(statement).setString(3, "SVO");
        verify(statement).setString(4, "DME");
        verify(statement).setInt(5, 200);
        verify(statement).setInt(6, 20);
        verify(statement).setLong(7, 1L);
        verify(statement).executeUpdate();
    }

    @Test
    void flight_delete() throws SQLException{
        flightRepository.deleteById(1L);

        verify(statement).setLong(1, 1L);
        verify(statement).executeUpdate();
    }
}
