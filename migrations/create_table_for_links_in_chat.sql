--liquibase formatted sql

--changeset admin:sample1_8
create table if not exists link_chat
(
    link_id integer,
    chat_id integer,
    primary key (link_id, chat_id),
    foreign key (link_id) references links (id) on delete cascade,
    foreign key (chat_id) references chat (chat_id) on delete cascade
    )

