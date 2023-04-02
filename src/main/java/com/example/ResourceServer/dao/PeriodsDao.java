package com.example.ResourceServer.dao;

import com.example.ResourceServer.domains.Period;
import com.example.ResourceServer.domains.Year;
import com.example.ResourceServer.repositories.PeriodRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Repository
public class PeriodsDao {

    private final PeriodRepository periodRepository;

    public PeriodsDao(PeriodRepository periodRepository) {
        this.periodRepository = periodRepository;
    }

    public Period getByNameOrCreatePeriod(String periodName, Year year){
        Optional<Period> periodOptional = periodRepository.getPeriodByYearAndPeriodName(year, periodName);
        if (periodOptional.isPresent()) return periodOptional.get();

        Period period = new Period();
        period.setPeriodName(periodName);
        period.setSubjectList(new ArrayList<>());
        period.setPeriodId(UUID.randomUUID().toString());
        return period;
    }
}
