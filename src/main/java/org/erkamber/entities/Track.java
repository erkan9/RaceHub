package org.erkamber.entities;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.Duration;
import java.util.List;

@Entity
@Table(name = "tracks")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Track {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private long trackId;

    @Column(name = "name", length = 50, unique = false, updatable = true, insertable = true, nullable = false)
    private String trackName;

    @Column(name = "city", length = 30, unique = false, updatable = true, insertable = true, nullable = false)
    private String city;

    @Column(name = "track_lenght_km", unique = false, updatable = true, insertable = true, nullable = false)
    private double trackLengthKms;

    @Column(name = "best_track_time", unique = false, updatable = true, insertable = true, nullable = false)
    private Duration bestTrackTime;

   // @OneToMany(mappedBy = "track", orphanRemoval = true, fetch = FetchType.LAZY)
   // private List<Race> races;

    @Column(name = "track_photo", length = 500, unique = false, updatable = true, insertable = true, nullable = true)
    private String trackPhoto;

    @PrePersist
    public void prePersist() {
        if (bestTrackTime == null) {
            bestTrackTime = Duration.ZERO;
        }
    }
}
