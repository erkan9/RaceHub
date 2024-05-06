package org.erkamber.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "statistics")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Statistic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private long statisticId;

    @Column(name = "driven_kms", updatable = true, insertable = true, nullable = true)
    private double drivenKms = 0.0;

    @Column(name = "driven_time_minutes", updatable = true, insertable = true, nullable = true)
    private int drivenTimeMinutes = 0;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "racer_id")
    private Racer racer;
}