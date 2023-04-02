package com.example.ResourceServer.dao;

import com.example.ResourceServer.domains.Profile;
import com.example.ResourceServer.domains.Year;
import com.example.ResourceServer.repositories.YearsRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Repository
public class YearsDao {

    private final YearsRepository yearsRepository;

    public YearsDao(YearsRepository yearsRepository) {
        this.yearsRepository = yearsRepository;
    }

    public Year getByNameOrCreateYear(String yearName, Profile profile){
        Optional<Year> optionalYear = yearsRepository.getYearByYearNameAndProfile(yearName, profile);
        if (optionalYear.isPresent()) return optionalYear.get();

        Year year = new Year();
        year.setYearName(yearName);
        year.setYearId(UUID.randomUUID().toString());
        year.setPeriodList(new ArrayList<>());
        return year;
    }
}
