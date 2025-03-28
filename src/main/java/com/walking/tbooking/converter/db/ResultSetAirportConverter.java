package com.walking.tbooking.converter.db;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ResultSetAirportConverter<T> {
    T convert(ResultSet rs) throws SQLException;

}
