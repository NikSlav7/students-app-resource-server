package com.example.ResourceServer.rest;


import com.example.ResourceServer.dao.PeriodsDao;
import com.example.ResourceServer.dao.ProfilesDao;
import com.example.ResourceServer.dao.YearsDao;
import com.example.ResourceServer.domains.Profile;
import com.example.ResourceServer.domains.Year;
import com.example.ResourceServer.dto.PeriodDeleteDTO;
import com.example.ResourceServer.exceptions.UserNotFoundException;
import com.example.ResourceServer.exceptions.WrongDataSentException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/period")
@RestController
public class PeriodService {


    private final PeriodsDao periodsDao;
    private final ProfilesDao profilesDao;

    private final YearsDao yearsDao;

    public PeriodService(PeriodsDao periodsDao, ProfilesDao profilesDao, YearsDao yearsDao) {
        this.periodsDao = periodsDao;
        this.profilesDao = profilesDao;
        this.yearsDao = yearsDao;
    }


    @PostMapping("/delete-period")
    public ResponseEntity deletePeriod(@RequestBody @Validated PeriodDeleteDTO periodDeleteDTO) throws UserNotFoundException, WrongDataSentException {
        Profile profile = profilesDao.getProfileById((String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        Year year = yearsDao.getYearByNameAndProfile(periodDeleteDTO.getYearName(), profile);
        periodsDao.deletePeriod(periodDeleteDTO.getPeriodName(), year);
        return ResponseEntity.ok(periodDeleteDTO.getPeriodName());
    }
}
