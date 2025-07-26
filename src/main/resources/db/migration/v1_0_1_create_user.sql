create type role_type as enum ('User', 'Admin');

create table user (
id                        bigserial       primary key,
email                     varchar(100)    not null,
password                  varchar(100)    not null,
fullname                  varchar(100)    not null,
role                      role_type       not null,
last_login                timestamp       not null,
is_blocked                boolean         not null

);