package org.erkamber.entities;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "karts")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Kart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private long kartId;

    @Column(name = "model", length = 25, unique = false, updatable = true, insertable = true, nullable = false)
    private String model;

    @Column(name = "horse_power", unique = false, updatable = true, insertable = true, nullable = false)
    private double horsePower;

    @Column(name = "kart_number", unique = true, updatable = false, insertable = true, nullable = false)
    private int kartNumber;

    @Column(name = "engine_cc", unique = false, updatable = false, insertable = true, nullable = false)
    private double engineCC;
}
