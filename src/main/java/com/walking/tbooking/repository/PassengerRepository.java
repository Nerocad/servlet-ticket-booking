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

    public PassengerRepository(DataSource dataSource, PassengerConverter converter) {
        this.dataSource = dataSource;
        this.converter = converter;
    }

    public Optional<Passenger> findById(Long id) {
        String sql = """
                select
                    p.id as passenger_id,
                    p.fullname as passenger_name,
                    p.gender as passenger_gender,
                    p.birth_date as passenger_birth_date,
                    p.passport_data as passenger_passport,
                    a.id as airport_id,
                    a.code as airport_code,
                    a.name as airport_name
                    a.address as airport_address
                from passenger p
                left join passenger_favorite_airport pfa
                    on p.id = pfa.passenger_id
                left join airport a
                    on pfa.airport_id = a.id
                where p.id =?
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();

            return converter.convert(rs);



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

    private void setParameters(Passenger passenger, PreparedStatement statement) throws SQLException {
        statement.setString(1, passenger.getFullName());
        statement.setObject(2, passenger.getGender());
        statement.setDate(3, java.sql.Date.valueOf(passenger.getBirthDate()));
        statement.setString(4, passenger.getPassportData());
    }

}


