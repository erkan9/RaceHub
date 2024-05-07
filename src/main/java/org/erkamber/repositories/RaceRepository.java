package org.erkamber.repositories;

import org.erkamber.entities.Kart;
import org.erkamber.entities.Race;
import org.erkamber.entities.Racer;
import org.erkamber.entities.Track;
import org.hibernate.loader.plan.build.internal.LoadGraphLoadPlanBuildingStrategy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RaceRepository extends JpaRepository<Race, LoadGraphLoadPlanBuildingStrategy> {

    List<Race> findRaceByRacerAndTrackAndRaceKart(Racer racer, Track track, Kart kart);
}
