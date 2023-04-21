package com.example.ResourceServer.rest;


import com.example.ResourceServer.dao.AbsenceDAO;
import com.example.ResourceServer.dto.AbsenceStatsDTO;
import com.example.ResourceServer.dao.ProfilesDao;
import com.example.ResourceServer.domains.Profile;
import com.example.ResourceServer.exceptions.UserNotFoundException;
import com.example.ResourceServer.exceptions.WrongDataSentException;
import lombok.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Service
@RestController
@RequestMapping("/api/absence")
public class AbsenceService {

    private final ProfilesDao profilesDao;

    private final AbsenceDAO absenceDAO;

    public AbsenceService(ProfilesDao profilesDao, AbsenceDAO absenceDAO) {
        this.profilesDao = profilesDao;
        this.absenceDAO = absenceDAO;
    }

    @GetMapping("/get-period-absence")
    public AbsenceStatsDTO getPeriodAbsenceStats(@RequestParam @NonNull String yearName, @RequestParam @NonNull String periodName) throws UserNotFoundException, WrongDataSentException {
        Profile profile = profilesDao.getProfileById(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        return new AbsenceStatsDTO(absenceDAO.getAbsenceStats(profile, yearName, periodName));
    }
}
