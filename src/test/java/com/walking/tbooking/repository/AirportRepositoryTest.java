package com.walking.tbooking.repository;

import com.walking.tbooking.converter.db.AirportConverter;
import com.walking.tbooking.model.Airport;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    private HikariDataSource dataSource;
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
    void findById_WhenAirportExists_ReturnsAirport() throws SQLException {
        Long id = 1L;
        Airport expectedAirport = new Airport(id, "SVO", "Шереметьево", "Москва");

        when(statement.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        when(converter.convert(rs)).thenReturn(Optional.of(expectedAirport));

        Optional<Airport> result = airportRepository.findById(id);

        assertTrue(result.isPresent());
        assertEquals(expectedAirport, result.get());
        verify(statement).setLong(1, id);
    }
}
