package com.example.ResourceServer.dao;

import com.example.ResourceServer.domains.Profile;
import com.example.ResourceServer.exceptions.UserNotFoundException;
import com.example.ResourceServer.exceptions.WrongDataSentException;
import com.example.ResourceServer.repositories.ProfilesRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.NoSuchElementException;

@Component
@Repository
public class ProfilesDao {

    private final ProfilesRepository profilesRepository;

    private final JdbcTemplate jdbcTemplate;

    public ProfilesDao(ProfilesRepository profilesRepository, JdbcTemplate jdbcTemplate) {
        this.profilesRepository = profilesRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    public Profile getProfileByEmailOrUsername(String cred) throws WrongDataSentException {
        return profilesRepository.findProfileByEmailOrUsername(cred, cred).orElseThrow(() -> new WrongDataSentException("No user with such credentials existing"));
    }

    public Profile getProfileById(String id) throws UserNotFoundException {
        return profilesRepository.findProfileByProfileId(id).orElseThrow(() -> new UserNotFoundException());
    }

    public Profile saveProfile(Profile profile){
        return profilesRepository.save(profile);
    }

    public boolean canAddProfile(Profile profile){
        return profilesRepository.findProfileByEmailOrUsername(profile.getEmail(), profile.getUsername()).isEmpty();
    }
}
