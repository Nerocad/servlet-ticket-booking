create table flight (
id                        bigserial       primary key,
departure_time            timestamp       not null,
arrival_time              timestamp       not null,
departure_airport_code    varchar(10)     not null,
arrival_airport_code      varchar(10)     not null,
total_seats               integer         not null,
available_seats           integer         not null,
check (available_seats <= total_seats)
);