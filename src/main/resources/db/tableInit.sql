DROP TABLE if EXISTS Company CASCADE;
CREATE TABLE Company (
    id bigint generated by default as identity,
    ceoName varchar(20) not null,
    companyName varchar(20) not null,
    primary key (id)
)