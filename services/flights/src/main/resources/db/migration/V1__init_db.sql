create table if not exists planes (
    id bigint primary key,
    model varchar(255) not null,
    capacity int not null
);

create table if not exists flights (
    id bigint primary key,
    departure_time timestamp not null,
    arrival_time timestamp not null,
    departure_airport varchar(255) not null,
    arrival_airport varchar(255) not null,
    plane_id bigint not null,
    CONSTRAINT fk_flights_planes
        FOREIGN KEY (plane_id) 
        REFERENCES planes(id)   
        ON DELETE RESTRICT
);


create sequence if not exists plane_seq start with 1 increment by 1;
create sequence if not exists flight_seq start with 1 increment by 1;