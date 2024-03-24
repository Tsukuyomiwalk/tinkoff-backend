--liquibase formatted sql

--changeset admin:sample1_10
create table if not exists chat
(
    chat_id       integer primary key
);
