package com.omerkoc.flights.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "flights")
@AllArgsConstructor
@NoArgsConstructor
public class Flights {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "flight_gen")
    @SequenceGenerator(name = "flight_gen", sequenceName = "flight_seq", allocationSize = 1)
    private String id;

    private String flightCode;
    private String departureAirport;
    private String arrivalAirport;
    private String departureTime;
    private String arrivalTime;
    private String status;

    private Plane plane;
}
