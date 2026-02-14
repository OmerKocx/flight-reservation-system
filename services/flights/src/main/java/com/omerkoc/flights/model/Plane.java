package com.omerkoc.flights.model;

import java.util.List;

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
@Table(name = "planes")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Plane {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "plane_gen")
    @SequenceGenerator(name = "plane_gen", sequenceName = "plane_seq", allocationSize = 1)
    private Integer id;
    private String model;
    private String capacity;

    @OneToMany(mappedBy = "plane", orphanRemoval = false)
    private List<Flights> flights;

    public void addFlight(Flights flight) {
        flights.add(flight);
        flight.setPlane(this);
    }

    public void removeFlight(Flights flight) {
        flights.remove(flight);
        flight.setPlane(null);
    }
}
