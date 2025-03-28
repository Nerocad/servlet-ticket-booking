create table passenger_favorite_airport (
passenger_id        bigint references passenger(id) on delete cascade,
airport_id          bigint references airport(id) on delete cascade,
primary key (passenger_id, airport_id)
);