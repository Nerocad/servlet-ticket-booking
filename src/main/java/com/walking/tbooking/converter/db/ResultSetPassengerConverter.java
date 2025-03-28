package com.walking.tbooking.converter.db;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ResultSetPassengerConverter<T> {
    T convert(ResultSet rs, Long passenger_id) throws SQLException;
}
