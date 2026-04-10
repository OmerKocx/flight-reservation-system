package com.omerkoc.flights.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "aircrafts")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Aircraft {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "aircraft_gen")
    @SequenceGenerator(name = "aircraft_gen", sequenceName = "aircraft_seq", allocationSize = 1)

    private Integer id;
    @Column(nullable = false)
    private String model;
    @Column(nullable = false)
    private Integer capacity;

    @OneToMany(mappedBy = "aircraft", orphanRemoval = false)
    private List<Flights> flights;

    public void addFlight(Flights flight) {
        flights.add(flight);
        flight.setAircraft(this);
    }

    public void removeFlight(Flights flight) {
        flights.remove(flight);
        flight.setAircraft(null);
    }
}
