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
@Table(name = "planes")
@AllArgsConstructor
@NoArgsConstructor
public class Plane {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "plane_gen")
    @SequenceGenerator(name = "plane_gen", sequenceName = "plane_seq", allocationSize = 1)
    private String id;
    private String model;
    private String capacity;
}
