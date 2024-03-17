--liquibase formatted sql

--changeset admin:sample1_9

create table if not exists links
(
    id         integer                             not null,
    link       text                                not null primary key,
    upd_at     timestamp default current_timestamp not null,
    checked_at timestamp default current_timestamp not null
);
