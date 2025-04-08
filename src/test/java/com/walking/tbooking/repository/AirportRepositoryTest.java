package com.walking.tbooking.repository;

import com.walking.tbooking.converter.db.AirportConverter;
import com.walking.tbooking.model.Airport;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;


import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


public class AirportRepositoryTest {

    private DataSource dataSource;
    private Connection connection;
    private PreparedStatement statement;
    private ResultSet rs;
    private AirportConverter converter;
    private AirportRepository airportRepository;

    @BeforeEach
    void setUp() throws SQLException {
        dataSource = mock(HikariDataSource.class);
        connection = mock(Connection.class);
        statement = mock(PreparedStatement.class);
        rs = mock(ResultSet.class);
        converter = mock(AirportConverter.class);

        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(statement);

        airportRepository = new AirportRepository(dataSource, converter);
    }

    @Test
    void findById_when_airport_exists() throws SQLException {

        Airport expectedAirport = new Airport(1L, "SVO", "Шереметьево", "Москва");

        when(statement.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        when(converter.convert(rs)).thenReturn(Optional.of(expectedAirport));

        Optional<Airport> result = airportRepository.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(expectedAirport, result.get());
        verify(statement).setLong(1, 1L);
    }
    @Test
    void findById_when_airport_not_exists() throws SQLException {
        when(statement.executeQuery()).thenReturn(rs);
        when(converter.convert(rs)).thenReturn(Optional.empty());

        Optional<Airport> result = airportRepository.findById(1L);

        assertTrue(result.isEmpty());

    }

    @Test
    void create_airport_thenReturns_airport_with_id() throws SQLException {
        Airport airport = new Airport(null, "SVO", "Шереметьево", "Москва");

        when(statement.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        when(rs.getLong(1)).thenReturn(1L);

        Airport createdAirport = airportRepository.create(airport);

        assertEquals(1L, createdAirport.getId());
        verify(statement).setString(1, "SVO");
        verify(statement).setString(2, "Шереметьево");
        verify(statement).setString(3, "Москва");
    }

    @Test
    void update_airport() throws SQLException {
        Airport airport = new Airport(1L, "SVO", "Шереметьево", "Москва");

        airportRepository.update(airport);

        verify(statement).setString(1,"SVO");
        verify(statement).setString(2,"Шереметьево");
        verify(statement).setString(3,"Москва");
        verify(statement).setLong(4, 1L);
        verify(statement).executeUpdate();

    }
    @Test
    void delete_airport() throws SQLException {
        airportRepository.deleteById(1L);

        verify(statement).setLong(1, 1L);
        verify(statement).executeUpdate();
    }
}



