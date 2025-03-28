package com.walking.tbooking.repository;

import com.walking.tbooking.converter.db.PassengerConverter;
import com.walking.tbooking.model.Airport;
import com.walking.tbooking.model.FavoriteAirports;
import com.walking.tbooking.model.Passenger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PassengerRepository {
    private final DataSource dataSource;
    private final PassengerConverter converter;
    private List<FavoriteAirports> airports = new ArrayList<>();

    public PassengerRepository(DataSource dataSource, PassengerConverter converter) {
        this.dataSource = dataSource;
        this.converter = converter;
    }

    public Optional<Passenger> findById(Long id) {
        String sql = "select * from passenger where id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();

            return converter.convert(rs, id);



        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при поиска пассажира");
        }


    }

    public Passenger update(Passenger passenger) {
        String sql = """
                update passenger
                  set fullname = ?,
                      gender = ?,
                      birth_date = ?,
                      passport_date = ?
                  where id = ?
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            setParameters(passenger, statement);
            statement.setLong(5, passenger.getId());
            statement.executeUpdate();

            return passenger;
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при обновлении пассажира");
        }
    }

    public Passenger create(Passenger passenger) {
        String sql = """
                insert into passenger
                (fullname, gender, birth_date, passport_data)
                values (?, ?, ?, ?)
                returning id
                """;

        try (Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {

            setParameters(passenger, statement);
            ResultSet rs = statement.executeQuery();
            rs.next();
            passenger.setId(rs.getLong(1));

            return passenger;
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при создании пассажира");
        }
    }

    public void deleteById(Long id) {
        String sql = "delete from passenger where id = ?";

        try (Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при удалении пассажира");
        }
    }



    public List<FavoriteAirports> getFavoriteAirportsByPassengerId(Long passengerId) {
        List<FavoriteAirports> airports = new ArrayList<>();

        String sql = """
                select * from favorite airport a
                join passenger_favorite_airport pfa
                on a.id = pfa.airport_id
                where pfa.passenger_id = ?
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, passengerId);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                FavoriteAirports airport = new FavoriteAirports();
                airport.setId(rs.getLong("id"));
                airport.setAirportCode(rs.getString("code"));
                airport.setAirportName(rs.getString("name"));
                airports.add(airport);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при поиске любимых аэропортов");
        }

        return airports;
    }

    private void setParameters(Passenger passenger, PreparedStatement statement) throws SQLException {
        statement.setString(1, passenger.getFullName());
        statement.setObject(2, passenger.getGender());
        statement.setDate(3, java.sql.Date.valueOf(passenger.getBirthDate()));
        statement.setString(4, passenger.getPassportData());
    }

}


