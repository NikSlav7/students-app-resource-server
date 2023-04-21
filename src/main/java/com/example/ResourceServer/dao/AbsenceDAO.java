package com.example.ResourceServer.dao;


import com.example.ResourceServer.domains.AbsenceStats;
import com.example.ResourceServer.domains.Period;
import com.example.ResourceServer.domains.Profile;
import com.example.ResourceServer.domains.Year;
import com.example.ResourceServer.exceptions.WrongDataSentException;
import com.example.ResourceServer.repositories.AbsenceStatsRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class AbsenceDAO {

    private final PeriodsDao periodsDao;

    private final YearsDao yearsDao;

    private final AbsenceStatsRepository absenceStatsRepository;

    public AbsenceDAO(PeriodsDao periodsDao, YearsDao yearsDao, AbsenceStatsRepository absenceStatsRepository) {
        this.periodsDao = periodsDao;
        this.yearsDao = yearsDao;
        this.absenceStatsRepository = absenceStatsRepository;
    }



    public AbsenceStats getAbsenceStats(Profile profile, String yearName, String periodName) throws WrongDataSentException {
        Year year = yearsDao.getYearByNameAndProfile(yearName, profile);
        Period period = periodsDao.getPeriod(periodName, year);
        AbsenceStats absenceStats = period.getAbsenceStats();
        return absenceStats;
    }
    public void removeProfileAbsenceStats(Period period){
        if (absenceStatsExistByPeriod(period)){
            absenceStatsRepository.delete(period.getAbsenceStats());
        }
    }

    public AbsenceStats getOrCreateNew(Year year, String periodName) throws WrongDataSentException {
        Period period =  periodsDao.getPeriod(periodName, year);
        return period.getAbsenceStats();
    }

    private boolean absenceStatsExistByPeriod(Period period){
        return absenceStatsRepository.existsAbsenceStatsByPeriod(period);
    }
}
