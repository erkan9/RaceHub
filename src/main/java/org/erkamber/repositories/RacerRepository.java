package org.erkamber.repositories;

import org.erkamber.entities.Race;
import org.erkamber.entities.Racer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RacerRepository extends JpaRepository<Racer, Long> {

    Optional<Racer> findRacerByEmailAndPassword(String username, String password);
}
