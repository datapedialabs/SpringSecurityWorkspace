-- ref. Appendix A of Spring Sec 3.1 manual

create table users(
    username varchar(256) not null primary key,
    password varchar(256) not null,
    enabled boolean not null
);
