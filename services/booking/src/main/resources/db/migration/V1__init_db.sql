create table if not exists bookings (
    id integer primary key,
    booking_code varchar(255) not null unique,
    customer_id varchar(255) not null,
    flight_id integer not null,
    booking_date timestamp not null,
    status varchar(50) not null
);

create sequence if not exists booking_seq start with 1 increment by 1;
