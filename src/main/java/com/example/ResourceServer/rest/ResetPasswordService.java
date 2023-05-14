package com.example.ResourceServer.rest;


import com.example.ResourceServer.dao.PasswordResetTokenDAO;
import com.example.ResourceServer.dao.ProfilesDao;
import com.example.ResourceServer.domains.PasswordResetToken;
import com.example.ResourceServer.domains.Profile;
import com.example.ResourceServer.exceptions.AuthServerError;
import com.example.ResourceServer.exceptions.UserNotFoundException;
import com.example.ResourceServer.request.PasswordResetRequest;
import com.example.ResourceServer.request.RequirePasswordResetRequest;
import com.example.ResourceServer.exceptions.NoSuchEntityException;
import com.example.ResourceServer.exceptions.WrongDataSentException;
import com.example.ResourceServer.mail.PasswordResetMailSender;
import com.example.ResourceServer.request.ValidateTokenRequest;
import com.example.ResourceServer.security.AuthManager;
import jakarta.mail.MessagingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/password-reset")
public class ResetPasswordService {

    private final ProfilesDao profilesDao;

    private final AuthManager authManager;
    private final PasswordResetTokenDAO passwordResetTokenDAO;
    private final PasswordResetMailSender passwordResetMailSender;

    public ResetPasswordService(ProfilesDao profilesDao, AuthManager authManager, PasswordResetTokenDAO passwordResetTokenDAO, PasswordResetMailSender passwordResetMailSender) {
        this.profilesDao = profilesDao;
        this.authManager = authManager;
        this.passwordResetTokenDAO = passwordResetTokenDAO;
        this.passwordResetMailSender = passwordResetMailSender;
    }

    @GetMapping("/check-token")
    public boolean checkToken(@RequestParam String token) throws NoSuchEntityException {
        return passwordResetTokenDAO.isValid(token);
    }


    @PostMapping("/require-reset")
    public ResponseEntity requirePasswordReset(@RequestBody RequirePasswordResetRequest resetRequest) throws WrongDataSentException, MessagingException {
        Profile profile = profilesDao.getProfileByEmailOrUsername(resetRequest.getCred());
        PasswordResetToken passwordResetToken = passwordResetTokenDAO.generateToken(profile);
        passwordResetMailSender.sendPasswordResetEmail(profile.getEmail(), profile.getUsername(), passwordResetToken);
        return ResponseEntity.ok(passwordResetToken.getToken());
    }

    @PostMapping("/reset")
    public ResponseEntity resetPassword(@RequestBody PasswordResetRequest resetRequest) throws NoSuchEntityException, WrongDataSentException, IOException, AuthServerError {
        validateResetRequest(resetRequest);
        Profile profile = passwordResetTokenDAO.getProfileByToken(resetRequest.getToken());
        authManager.resetPassword(profile, resetRequest.getPassword(), resetRequest.getToken());
        return ResponseEntity.ok(true);
    }

    @PostMapping("/validate")
    public ResponseEntity validateToken(@RequestBody ValidateTokenRequest validateTokenRequest) throws NoSuchEntityException, UserNotFoundException {
        if (!profilesDao.getProfileById(passwordResetTokenDAO.getProfileByToken(validateTokenRequest.getToken()).getProfileId()).getUsername().equals(validateTokenRequest.getUsername())
                || !passwordResetTokenDAO.isValid(validateTokenRequest.getToken())) return ResponseEntity.badRequest().build();
        else return ResponseEntity.ok(true);
    }

    private void validateResetRequest(PasswordResetRequest resetRequest) throws NoSuchEntityException, WrongDataSentException {
        if (!resetRequest.getPassword().equals(resetRequest.getPassword()) || !passwordResetTokenDAO.isValid(resetRequest.getToken())) throw new WrongDataSentException("Passwords doesn't match");
    }
}
