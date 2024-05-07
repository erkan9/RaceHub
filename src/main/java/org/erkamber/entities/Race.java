package org.erkamber.entities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "races")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Race {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int raceId;

    @ManyToOne
    @JoinColumn(name = "racer_id")
    private Racer racer;

    @OneToMany(mappedBy = "race", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Lap> laps;

    @ManyToOne
    @JoinColumn(name = "race_kart_id")
    private Kart raceKart;

    @ManyToOne
    @JoinColumn(name = "track_id")
    Track track;

    public Race(Racer racer, List<Lap> laps, Kart raceKart, Track track) {
        this.racer = racer;
        this.laps = laps;
        this.raceKart = raceKart;
        this.track = track;
    }
}
