package com.example.ResourceServer.rest;


import com.example.ResourceServer.dao.ProfilesDao;
import com.example.ResourceServer.dao.YearsDao;
import com.example.ResourceServer.domains.Period;
import com.example.ResourceServer.domains.Profile;
import com.example.ResourceServer.domains.SubjectWithIntList;
import com.example.ResourceServer.domains.Year;
import com.example.ResourceServer.exceptions.UserNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/profile")
public class ProfileService {

    private final ProfilesDao profilesDao;

    private final YearsDao yearsDao;

    public ProfileService(ProfilesDao profilesDao, YearsDao yearsDao) {
        this.profilesDao = profilesDao;
        this.yearsDao = yearsDao;
    }

    @GetMapping("/get-all-mark-averages")
    public Map<String, Map<String, List<SubjectWithIntList>>> getAllMarkAverages() throws UserNotFoundException {
        Profile profile = profilesDao.getProfileById(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        return yearsDao.getProfileMarksData(profile);
    }

}
