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

