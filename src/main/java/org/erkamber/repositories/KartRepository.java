package org.erkamber.repositories;

import org.erkamber.entities.Kart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KartRepository extends JpaRepository<Kart, Long> {
}
