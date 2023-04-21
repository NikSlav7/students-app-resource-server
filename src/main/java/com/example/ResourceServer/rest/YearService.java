package com.example.ResourceServer.rest;

import com.example.ResourceServer.dao.ProfilesDao;
import com.example.ResourceServer.dao.YearsDao;
import com.example.ResourceServer.domains.Profile;
import com.example.ResourceServer.domains.SubjectWithIntList;
import com.example.ResourceServer.dto.YearDTO;
import com.example.ResourceServer.exceptions.UserNotFoundException;
import com.example.ResourceServer.exceptions.WrongDataSentException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/year")
public class YearService {


    private final YearsDao yearsDao;

    private final ProfilesDao profilesDao;

    public YearService(YearsDao yearsDao, ProfilesDao profilesDao) {
        this.yearsDao = yearsDao;
        this.profilesDao = profilesDao;
    }


    @PostMapping("/delete-year/{yearName}")
    public ResponseEntity deleteYear(@PathVariable String yearName) throws UserNotFoundException, WrongDataSentException {
        Profile profile = profilesDao.getProfileById(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        yearsDao.deleteYear(yearName, profile);
        return ResponseEntity.ok(yearName);
    }

    @PostMapping("/create-new-year")
    public ResponseEntity createNewYear(@RequestBody @Validated YearDTO yearDTO) throws UserNotFoundException {
        String profileId = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        Profile profile = profilesDao.getProfileById(profileId);
        yearsDao.createNewYear(yearDTO.getName(), profile);
        return ResponseEntity.ok(true);
    }


    @GetMapping("/get-year-names")
    public List<String> getYearNames(){
        return yearsDao.
                getSimplifiedYearsByProfileId(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()).
                stream().map(year -> year.getYearName()).
                collect(Collectors.toList());
    }

    @GetMapping("/get-year-data")
    public Map<String, List<SubjectWithIntList>> getYearData(@RequestParam String yearName) throws UserNotFoundException, WrongDataSentException {
        String profileId = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        return yearsDao.getAllMarksByYear(yearsDao.getYearByNameAndProfile(yearName, profilesDao.getProfileById(profileId)));
    }
}
