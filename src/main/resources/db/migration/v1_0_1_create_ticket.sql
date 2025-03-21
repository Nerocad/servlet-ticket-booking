create type service_class_type as enum ('Economy', 'Business', 'First');

create table if not exists ticket (
id                        bigserial              primary key,
service_class             service_class_type     not null,
seat_number               integer                not null,
baggage_allowance         varchar(100)           not null,
flight_id                 BIGINT                 not null,
passenger_id              BIGINT                 not null,
constraint fk_flight  foreign key (flight_id) references flight(id),
constraint fk_passenger foreign key (passenger_id)  references passenger(id)
);