package com.walking.tbooking.repository;

import com.walking.tbooking.converter.db.TicketConverter;
import com.walking.tbooking.model.ServiceClass;
import com.walking.tbooking.model.Ticket;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class TicketRepository {

    private final DataSource dataSource;
    private final TicketConverter converter;

    public TicketRepository(DataSource dataSource, TicketConverter converter) {
        this.dataSource = dataSource;
        this.converter = converter;
    }

    public Optional<Ticket> findById(Long id) {
        String sql = "select * from ticket where id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();

            return converter.convert(rs);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка поиска билета");
        }
    }

    public Ticket create(Ticket ticket) {
        if (!existsFlight(ticket.getFlightId())) {
            throw new IllegalArgumentException("Рейс не существует: " + ticket.getFlightId());
        }

        if (!existsPassenger(ticket.getPassengerId())) {
            throw new IllegalArgumentException("Пассажир не существует: " + ticket.getPassengerId());
        }

        if (!isSeatAvailable(ticket.getFlightId(), ticket.getSeatNumber())) {
            throw new IllegalArgumentException("Место " + ticket.getSeatNumber() + " уже занято");
        }

        String sql = """
                insert into ticket (service_class, seat_number, baggage_allowance,
                                    flight_id, passenger_id) values (?, ?, ?, ?, ?)
                                    returning id;
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            setParameters(ticket, statement);
            ResultSet rs = statement.executeQuery();
            rs.next();
            ticket.setId(rs.getLong(1));

            return ticket;
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка создания билета");
        }
    }


    private boolean existsFlight(Long flightId) {
        String sql = "select 1 from flight where id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, flightId);
            ResultSet rs = statement.executeQuery();

            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка проверки рейса");
        }
    }

    private boolean existsPassenger(Long passengerId) {
        String sql = "select 1 from passenger where id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, passengerId);
            ResultSet rs = statement.executeQuery();

            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка проверки пассажира");
        }
    }

    private boolean isSeatAvailable(Long flightId, int seatNumber) {
        String sql = "select 1 from ticket where flight_id = ? and seat_number = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, flightId);
            statement.setInt(2, seatNumber);
            ResultSet rs = statement.executeQuery();

            return !rs.next();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при проверки посадочного места");
        }
    }

    private void setParameters(Ticket ticket, PreparedStatement statement) throws SQLException {
        statement.setString(1, ticket.getServiceClass().name());
        statement.setInt(2, ticket.getSeatNumber());
        statement.setString(3, ticket.getBaggageAllowance());
        statement.setLong(4, ticket.getFlightId());
        statement.setLong(5, ticket.getPassengerId());
    }
}
