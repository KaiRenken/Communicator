create table chat
(
    id      uuid primary key,
    name    varchar(1024) not null
);

create table message
(
    id          uuid primary key,
    chat_id     uuid not null,
    content     varchar(1024) not null,
    constraint fk_message_chat_id foreign key(chat_id) references chat(id)
);