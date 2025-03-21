create table if not exists airport (
    id              bigserial       primary key,
    code            varchar(10)     not null,
    name            varchar(100)    not null,
    address         varchar(100)    not null
);