package com.walking.tbooking.repository;

import com.walking.tbooking.converter.db.TicketConverter;

import javax.sql.DataSource;

public class TicketRepository {
    private final DataSource dataSource;
    private final TicketConverter converter;

    public TicketRepository(DataSource dataSource, TicketConverter converter) {
        this.dataSource = dataSource;
        this.converter = converter;
    }


}
