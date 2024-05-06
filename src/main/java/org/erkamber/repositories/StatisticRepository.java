package org.erkamber.repositories;

import net.sf.saxon.tree.tiny.Statistics;
import org.erkamber.entities.Statistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatisticRepository extends JpaRepository<Statistic, Long> {
}
