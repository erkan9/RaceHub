package org.erkamber.repositories;

import org.erkamber.dtos.TrackDTO;
import org.erkamber.entities.Racer;
import org.erkamber.entities.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrackRepository extends JpaRepository<Track, Long> {

    List<Track> findTrackByCity(String city);

}
