package org.erkamber.repositories;

import org.erkamber.entities.Kart;
import org.erkamber.entities.Race;
import org.erkamber.entities.Racer;
import org.erkamber.entities.Track;
import org.hibernate.loader.plan.build.internal.LoadGraphLoadPlanBuildingStrategy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface RaceRepository extends JpaRepository<Race, Long> {

    Optional<Race> findFirstByRacerOrderByLapsLapDateDesc(Racer racer);

    List<Race> findRaceByRacerAndTrackAndRaceKart(Racer racer, Track track, Kart kart);

    @Query("SELECT MIN(lap.lapTime) FROM Race race " +
            "JOIN race.laps lap " +
            "WHERE race.track = :track AND race.raceKart = :kart AND race.racer = :racer")
    Duration findBestLapTimeByTrackAndKartAndRacer(@Param("track") Track track,
                                                   @Param("kart") Kart kart,
                                                   @Param("racer") Racer racer);

    @Query("SELECT MIN(lap.lapDate) FROM Race race " +
            "JOIN race.laps lap " +
            "WHERE race.track = :track AND race.raceKart = :kart AND race.racer = :racer")
    LocalDate findBestLapDateByTrackAndKartAndRacer(@Param("track") Track track,
                                                    @Param("kart") Kart kart,
                                                    @Param("racer") Racer racer);

    @Query("SELECT MIN(l.lapTime) FROM Race r " +
            "JOIN r.laps l " +
            "WHERE r.track = :track AND r.raceKart = :kart AND r.racer = :racer " +
            "AND r IN (SELECT MAX(r1) FROM Race r1 WHERE r1.racer = :racer)")
    Duration findBestLapTimeForLastRaceByTrackAndKartAndRacer(@Param("track") Track track,
                                                              @Param("kart") Kart kart,
                                                              @Param("racer") Racer racer);

    @Query("SELECT MAX(lap.lapDate) FROM Race race " +
            "JOIN race.laps lap " +
            "WHERE race.track = :track AND race.raceKart = :kart AND race.racer = :racer")
    LocalDate findLastRaceDateByTrackAndKartAndRacer(@Param("track") Track track,
                                                     @Param("kart") Kart kart,
                                                     @Param("racer") Racer racer);
    List<Race> findRaceByRacer(Racer racer);

}
