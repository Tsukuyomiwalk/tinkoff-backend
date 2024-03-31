--liquibase formatted sql

--changeset admin:sample1_9

create table if not exists links
(
    id         integer primary key                 not null,
    link       text unique                         not null,
    upd_at     timestamp default current_timestamp not null,
    checked_at timestamp default current_timestamp not null
);
