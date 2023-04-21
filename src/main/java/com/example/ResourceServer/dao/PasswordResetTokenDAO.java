package com.example.ResourceServer.dao;


import com.example.ResourceServer.domains.PasswordResetToken;
import com.example.ResourceServer.domains.Profile;
import com.example.ResourceServer.exceptions.NoSuchEntityException;
import com.example.ResourceServer.repositories.PasswordResetTokenRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.UUID;

@Repository
public class PasswordResetTokenDAO {


    private final PasswordResetTokenRepository passwordResetTokenRepository;

    public PasswordResetTokenDAO(PasswordResetTokenRepository passwordResetTokenRepository) {
        this.passwordResetTokenRepository = passwordResetTokenRepository;
    }

    public PasswordResetToken getPasswordResetTokenByToken(String token) throws NoSuchEntityException {
        return passwordResetTokenRepository.getPasswordResetTokenByToken(token).orElseThrow(() -> new NoSuchEntityException("There is no such token existing"));
    }

    public PasswordResetToken generateToken(Profile profile){
        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setToken(UUID.randomUUID().toString());
        passwordResetToken.setProfile(profile);
        passwordResetToken.setExpiresAt(LocalDateTime.now().plusMinutes(15));
        return passwordResetTokenRepository.save(passwordResetToken);
    }

    public PasswordResetToken saveToken(PasswordResetToken passwordResetToken){
        return passwordResetTokenRepository.save(passwordResetToken);
    }

    public Profile getProfileByToken(String token) throws NoSuchEntityException {
        return getPasswordResetTokenByToken(token).getProfile();
    }

    public boolean isValid(String token) throws NoSuchEntityException {
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.getPasswordResetTokenByToken(token).orElseThrow(() -> new NoSuchEntityException("The token is invalid"));
        return passwordResetToken.getExpiresAt().isAfter(LocalDateTime.now());
    }


}
