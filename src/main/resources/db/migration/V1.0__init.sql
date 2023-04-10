create table chat
(
    id      uuid primary key,
    name    varchar(1024) not null
);

create table member
(
    id      uuid primary key,
    chat_id uuid not null
);

create table message
(
    id          uuid primary key,
    sender_id   uuid not null,
    chat_id     uuid not null,
    content     varchar(1024) not null
);