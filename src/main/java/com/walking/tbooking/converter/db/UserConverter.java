package com.walking.tbooking.converter.db;

import com.walking.tbooking.model.Role;
import com.walking.tbooking.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;

public class UserConverter implements ResultSetConverter<Optional<User>> {

    @Override
    public Optional<User> convert(ResultSet rs) throws SQLException {
        return rs.next() ? Optional.of(mapRow(rs)) : Optional.empty();
    }

    private User mapRow(ResultSet rs) throws SQLException {
        User user = new User();

        user.setId(rs.getLong("id"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setFullName(rs.getString("fullname"));
        user.setRole(Role.valueOf(rs.getString("role")));
        user.setLastLogin(rs.getObject("last_login", LocalDateTime.class));
        user.setBlocked(rs.getBoolean("is_blocked"));

        return user;
    }
}
