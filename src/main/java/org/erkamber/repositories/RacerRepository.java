package org.erkamber.repositories;

import org.erkamber.entities.Racer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RacerRepository extends JpaRepository<Racer, Long> {

    Optional<Racer> findRacerByEmail(String email);

    Optional<Racer> findRacerByEmailAndPassword(String email, String password);
}
