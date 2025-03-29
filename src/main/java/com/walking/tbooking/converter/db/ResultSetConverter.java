package com.walking.tbooking.converter.db;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ResultSetConverter<T> {
    T convert(ResultSet rs,  Long passenger_id) throws SQLException;

    default T convert(ResultSet rs) throws SQLException {
        return convert(rs, null);
    }

}
