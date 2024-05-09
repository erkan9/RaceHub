package org.erkamber.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDate;

@Entity
@Table(name = "laps")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Lap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private long lapId;

    @ManyToOne()
    @JoinColumn(name = "race_id")
    private Race race;

    private Duration lapTime;

    private boolean isBestTime;

    private LocalDate lapDate = LocalDate.now();
}