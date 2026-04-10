create table if not exists aircrafts (
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
    aircraft_id integer not null,
    capacity integer not null,
    CONSTRAINT fk_flights_aircrafts
        FOREIGN KEY (aircraft_id) 
        REFERENCES aircrafts(id)   
        ON DELETE RESTRICT
);


create sequence if not exists aircraft_seq start with 1 increment by 1;
create sequence if not exists flight_seq start with 1 increment by 1;