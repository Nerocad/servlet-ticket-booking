package com.walking.tbooking.repository;

import com.walking.tbooking.converter.db.UserConverter;
import com.walking.tbooking.model.User;

import javax.sql.DataSource;
import java.util.Optional;

public class UserRepository {

    private final DataSource dataSource;
    private final UserConverter userConverter;

    public UserRepository(DataSource dataSource, UserConverter userConverter) {
        this.dataSource = dataSource;
        this.userConverter = userConverter;
    }

    public Optional<User> findById(Long id) {
        String sql = "select * from user where id = ?";
    }
}
