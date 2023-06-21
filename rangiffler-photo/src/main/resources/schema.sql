-- create database "rangiffler-photo" with owner postgres;

create extension if not exists "uuid-ossp";

create table if not exists photos
(
    id                      UUID unique        not null,
    username                varchar(50)        not null,
    description             varchar(255),
    photo                   bytea,
    primary key (id)
);

alter table photos
    owner to postgres;

create table if not exists countries
(
--    id                      UUID unique  not null default uuid_generate_v1() primary key,
    code                    varchar(50)        not null,
    name                    varchar(255)       not null,
    photo_id UUID unique                       not null,
    constraint fk_photos_countries foreign key (photo_id) references photos (id)
);

alter table countries
    owner to postgres;