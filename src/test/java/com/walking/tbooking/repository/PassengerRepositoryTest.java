package com.walking.tbooking.repository;

import com.walking.tbooking.converter.db.PassengerConverter;
import com.walking.tbooking.model.FavoriteAirports;
import com.walking.tbooking.model.Gender;
import com.walking.tbooking.model.Passenger;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class PassengerRepositoryTest {

    private DataSource dataSource;
    private Connection connection;
    private PreparedStatement statement;
    private ResultSet rs;
    private PassengerConverter converter;
    private PassengerRepository passengerRepository;

    @BeforeEach
    void setUp() throws SQLException {
        dataSource = mock(HikariDataSource.class);
        connection = mock(Connection.class);
        statement = mock(PreparedStatement.class);
        rs = mock(ResultSet.class);
        converter = mock(PassengerConverter.class);
        passengerRepository = mock(PassengerRepository.class);

        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(statement);

        passengerRepository = new PassengerRepository(dataSource, converter);
    }

    @Test
    void findById_shouldReturnPassengerWithAirports() throws SQLException {
        Long id = 1L;
        List<FavoriteAirports> airports = new ArrayList<>();
        airports.add(new FavoriteAirports(1L, "SVO", "Шереметьево"));
        airports.add(new FavoriteAirports(2L, "VKO", "Внуково"));
        airports.add(new FavoriteAirports(3L, "LED", "Пулково"));
        Passenger expectedPassenger = new Passenger(1L, "John", Gender.MALE,
                LocalDate.of(1990, 1, 1), "45 10 897456",
                airports);

        when(statement.executeQuery()).thenReturn(rs);
        when(converter.convert(rs)).thenReturn(Optional.of(expectedPassenger));

        Optional<Passenger> result = passengerRepository.findById(id);

        assertTrue(result.isPresent());
        assertEquals(expectedPassenger, result.get());
        verify(statement).setLong(1, id);
    }
}
