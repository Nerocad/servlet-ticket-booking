create table if not exists favorite_airport (
id              bigserial       primary key,
airportCode     varchar(10)     not null,
airportName     varchar(100)    not null
);