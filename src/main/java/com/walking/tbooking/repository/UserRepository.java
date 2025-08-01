package com.walking.tbooking.repository;

import com.walking.tbooking.converter.db.UserConverter;
import com.walking.tbooking.model.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Optional;

public class UserRepository {

    private final DataSource dataSource;
    private final UserConverter converter;

    public UserRepository(DataSource dataSource, UserConverter converter) {
        this.dataSource = dataSource;
        this.converter = converter;
    }

    public Optional<User> findById(Long id) {
        String sql = "select * from user where id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            return converter.convert(rs);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при поиске пользователя");
        }
    }

    public User create(User user) {
        String sql = """
                insert into(email, password, fullname, role,
                            last_login, is_blocked) values
                            (?, ?, ?, ?, ?, ?) returning id;
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            setParameters(user, statement);
            ResultSet rs = statement.executeQuery();
            rs.next();
            user.setId(rs.getLong(1));

            return user;
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка создания пользователя");
        }
    }

    public User update(User user) {
        String sql = """
                update user
                    set email = ?,
                        password = ?,
                        fullname = ?,
                        last_login = ?,
                        role = ?,
                        is_blocked = ?
                    where id = ?;
                """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            setParameters(user, statement);
            statement.setLong(7, user.getId());
            statement.executeUpdate();

            return user;
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка обновления пользователя");
        }
    }

    public void deleteById(Long id) {
        String sql = "delete from user where id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка удаления пользователя");
        }
    }

    private void setParameters(User user, PreparedStatement statement) throws SQLException {
        statement.setString(1, user.getEmail());
        statement.setString(2, user.getPassword());
        statement.setString(3, user.getFullName());
        statement.setObject(4, user.getRole());
        statement.setTimestamp(5, java.sql.Timestamp.valueOf(user.getLastLogin()));
        statement.setBoolean(6, user.isBlocked());
    }
}
