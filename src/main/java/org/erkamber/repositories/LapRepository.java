package org.erkamber.repositories;

import org.erkamber.entities.Lap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LapRepository extends JpaRepository<Lap, Integer> {}
