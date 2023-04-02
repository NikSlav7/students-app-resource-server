package com.example.ResourceServer.repositories;

import com.example.ResourceServer.domains.Period;
import com.example.ResourceServer.domains.Year;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PeriodRepository extends JpaRepository<Period, String> {

    Optional<Period> getPeriodByYearAndPeriodName(Year year, String periodName);
}
