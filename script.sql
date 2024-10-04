create table categories
(
    id   bigserial
        primary key,
    name varchar(255)
);

create table users
(
    id        bigserial
        primary key,
    user_name varchar(255)
);

create table news
(
    id          bigserial
        primary key,
    date        timestamp(6) with time zone,
    text        varchar(255),
    title       varchar(255),
    category_id bigint
        constraint fk6itmfjj4ma8lfpj10jx24mhvx
            references categories,
    user_id     bigint
        constraint fki09n75txtudw1kawj5o7i8xag
            references users
);

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


