create table airport (
    id              bigserial       primary key,
    code            varchar(10)     unique, not null,
    name            varchar(100)    not null,
    address         varchar(100)    not null
);