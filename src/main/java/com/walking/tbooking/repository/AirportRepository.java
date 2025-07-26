package com.walking.tbooking.repository;

import com.walking.tbooking.converter.db.AirportConverter;
import com.walking.tbooking.model.Airport;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class AirportRepository {

    private final DataSource dataSource;
    private final AirportConverter converter;

    public AirportRepository(DataSource dataSource, AirportConverter converter) {
        this.dataSource = dataSource;
        this.converter = converter;
    }

    public Optional<Airport> findById(Long id) {
        String sql = "select * from airport where id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            return converter.convert(rs);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка получения аэропорта по id");
        }
    }

    public Airport create(Airport airport) {
        String sql = """
                insert into airport (code, name, address)
                values (?, ?, ?)
                returning id
                """;

        try (Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {

            setParameters(airport, statement);
            ResultSet rs = statement.executeQuery();
            rs.next();
            airport.setId(rs.getLong(1));

            return airport;

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при создании аэропорта");
        }
    }

    public Airport update(Airport airport) {
        String sql = """
                update airport
                set code = ?,
                    name = ?,
                    address = ?
                where id = ?
                """;

        try (Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {

            setParameters(airport, statement);
            statement.setLong(4, airport.getId());
            statement.executeUpdate();

            return airport;
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при обновлении аэропорта");
        }
    }

    public void deleteById(Long id) {
        String sql = "delete from airport where id = ?";

        try (Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new  RuntimeException("Ошибка при удалении аэропорта");
        }
    }

    private void setParameters(Airport airport, PreparedStatement statement) throws SQLException {
        statement.setString(1, airport.getCode());
        statement.setString(2, airport.getName());
        statement.setString(3, airport.getAddress());
    }
}
