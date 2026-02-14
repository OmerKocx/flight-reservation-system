create table if not exists planes (
    id integer primary key,
    model varchar(255) not null,
    capacity integer not null
);

create table if not exists flights (
    id integer primary key,
    flight_code varchar(255) not null,
    departure_airport varchar(255) not null,
    arrival_airport varchar(255) not null,
    departure_time timestamp not null,
    arrival_time timestamp not null,
    status varchar(255) not null,
    plane_id integer not null,
    CONSTRAINT fk_flights_planes
        FOREIGN KEY (plane_id) 
        REFERENCES planes(id)   
        ON DELETE RESTRICT
);


create sequence if not exists plane_seq start with 1 increment by 1;
create sequence if not exists flight_seq start with 1 increment by 1;