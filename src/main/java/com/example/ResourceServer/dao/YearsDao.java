package com.example.ResourceServer.dao;

import com.example.ResourceServer.domains.*;
import com.example.ResourceServer.exceptions.EntityExistsException;
import com.example.ResourceServer.exceptions.WrongDataSentException;
import com.example.ResourceServer.repositories.YearsRepository;
import com.example.ResourceServer.rowMappers.SimplifiedYearRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.*;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class YearsDao {

    private final YearsRepository yearsRepository;

    private final JdbcTemplate jdbcTemplate;

    public YearsDao(YearsRepository yearsRepository, JdbcTemplate jdbcTemplate) {
        this.yearsRepository = yearsRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    public Year createNewYear(String yearName, Profile profile){
        Year year = new Year();
        year.setYearId(UUID.randomUUID().toString());
        year.setProfile(profile);
        year.setYearName(yearName);
        yearsRepository.save(year);
        return year;
    }

    public void deleteYear(String yearName, Profile profile) throws WrongDataSentException {
        yearsRepository.delete(yearsRepository.getYearByYearNameAndProfile(yearName, profile).orElseThrow(() -> new WrongDataSentException("There is no such year existing")));
    }

    public Year getYearByNameAndProfile(String name, Profile profile) throws WrongDataSentException {
        return yearsRepository.getYearByYearNameAndProfile(name, profile).orElseThrow(() -> new WrongDataSentException("Data that was sent is wrong"));
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

    public Map<String, Map<String, List<SubjectWithIntList>>> getProfileMarksData(Profile profile){
        List<Year> yearList = getAllProfileYears(profile);
        Map<String, Map<String, List<SubjectWithIntList>>> map = new LinkedHashMap<>();
        for (Year year : yearList){
            map.put(year.getYearName(), getAllMarksByYear(year));
        }
        return map;
    }

    public List<Year> getSimplifiedYearsByProfileId(String profileId){
        return jdbcTemplate.query("SELECT * FROM year WHERE profile_profile_id = ?", new SimplifiedYearRowMapper(), profileId);
    }
    public Map<String, List<SubjectWithIntList>> getAllMarksByYear(Year year){
        List<Period> periods = year.getPeriodList();
        Map<String, List<SubjectWithIntList>> periodMap = new LinkedHashMap<>();
        for (Period period : periods){
            periodMap.put(period.getPeriodName(), period.getSubjectList().stream().map(subject -> new SubjectWithIntList(subject)).collect(Collectors.toList()));
        }
        return periodMap;
    }
    private List<Year> getAllProfileYears(Profile profile){
        return yearsRepository.getYearsByProfile(profile);
    }

    public void checkIfYearNameAlreadyExists(String yearName, Profile profile) throws EntityExistsException {
        if (yearsRepository.existsByProfileAndYearName(profile, yearName)) throw new EntityExistsException("There is already a year with such name");
    }


}
