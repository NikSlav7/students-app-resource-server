package com.example.ResourceServer.rest;

import com.example.ResourceServer.dao.ProfilesDao;
import com.example.ResourceServer.domains.Profile;
import com.example.ResourceServer.dto.UserDTO;
import com.example.ResourceServer.exceptions.UserNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/profile/")
public class ProfileDetailsService {

    private final ProfilesDao profilesDao;

    public ProfileDetailsService(ProfilesDao profilesDao) {
        this.profilesDao = profilesDao;
    }

    @GetMapping("get-details")
    public UserDTO getUserDetails() throws UserNotFoundException {
        String id = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        Profile profile = profilesDao.getProfileById(id);
        return new UserDTO(profile);
    }
}
