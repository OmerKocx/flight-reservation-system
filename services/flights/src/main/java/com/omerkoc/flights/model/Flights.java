package com.omerkoc.flights.model;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "flights")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Flights {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "flight_gen")
    @SequenceGenerator(name = "flight_gen", sequenceName = "flight_seq", allocationSize = 1)
    private Integer id;

    @Column(name = "flight_code", nullable = false)
    private String flightCode;

    @Column(name = "departure_airport", nullable = false)
    private String departureAirport;

    @Column(name = "arrival_airport", nullable = false)
    private String arrivalAirport;

    @Column(name = "departure_time", nullable = false)
    private LocalDateTime departureTime;

    @Column(name = "arrival_time", nullable = false)
    private LocalDateTime arrivalTime;

    @Column(nullable = false)
    private String status;

    @ManyToOne
    @JoinColumn(name = "plane_id", nullable = false) // İŞTE KRİTİK NOKTA BURASI!
    private Plane plane;
}