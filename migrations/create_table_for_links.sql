--liquibase formatted sql

--changeset admin:sample1_9

create table if not exists links
(
    id           integer primary key,
    link       text      not null,
    chat_id      integer   not null,
    created_at  timestamp with time zone default current_timestamp not null
);
