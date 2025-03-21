create table passenger_favorite_airports (
passenger_id        bigint references passenger(id) on delete cascade,
airport_id          bigint references favorite_airports(id) on delete cascade,
primary key (passenger_id, airport_id)
);