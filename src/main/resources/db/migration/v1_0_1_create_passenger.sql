create type gender_type as enum ('Male', 'Female');

create table passenger (
id                        bigserial       primary key,
fullname                  varchar(100)    not null,
gender                    gender_type     not null,
birth_date                date            not null,
passport_data             varchar(100)    not null
);