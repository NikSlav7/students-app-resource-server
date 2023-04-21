package com.example.ResourceServer.repositories;

import com.example.ResourceServer.domains.AbsenceStats;
import com.example.ResourceServer.domains.Period;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AbsenceStatsRepository extends JpaRepository<AbsenceStats, String> {
    boolean existsAbsenceStatsByPeriod(Period period);
}
