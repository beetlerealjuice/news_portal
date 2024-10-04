create table comments
(
    id         bigserial
        primary key,
    create_at  timestamp(6) with time zone,
    text       varchar(255),
    updated_at timestamp(6) with time zone,
    news_id    bigint
        constraint fkqx89vg0vuof2ninmn5x5eqau2
            references news
);

