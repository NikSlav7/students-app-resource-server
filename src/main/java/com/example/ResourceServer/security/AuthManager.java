package com.example.ResourceServer.security;


import com.example.ResourceServer.dao.ProfilesDao;
import com.example.ResourceServer.domains.Profile;
import com.example.ResourceServer.dto.RegistrationDTO;
import com.example.ResourceServer.dto.TokenDTO;
import com.example.ResourceServer.dto.UsernamePasswordRegistrationDTO;
import com.example.ResourceServer.exceptions.AuthServerError;
import com.example.ResourceServer.exceptions.BadTokenException;
import com.example.ResourceServer.exceptions.CredentialsInUseException;
import com.example.ResourceServer.exceptions.WrongDataSentException;
import com.example.ResourceServer.request.PasswordResetRequestAuthServer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.net.ssl.SSLEngineResult;
import java.awt.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;


@Component
@RestController
@RequestMapping("/api/auth")
public class AuthManager {

    @Value("${authserver.domain}")
    private String authServer;

    private final ProfilesDao profilesDao;

    public AuthManager(ProfilesDao profilesDao) {
        this.profilesDao = profilesDao;
    }

    private Logger logger = LogManager.getLogger();

    @PostMapping("/register")
    public ResponseEntity<Profile> register(@RequestBody RegistrationDTO registrationDTO) throws IOException, CredentialsInUseException {
        Profile profile = new Profile(registrationDTO);
        if(!profilesDao.canAddProfile(profile)) throw new CredentialsInUseException("Credentials already used");
        profile.setProfileId(registerProfile(registrationDTO));
        return ResponseEntity.ok(profilesDao.saveProfile(profile));
    }

    public void resetPassword(Profile profile, String newPassword, String token) throws IOException, InternalError, AuthServerError {
        URL url = new URL("http://" + authServer + "/api/auth/change-password");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type","application/json");
        connection.setDoOutput(true);
        String data = new ObjectMapper().writeValueAsString(new PasswordResetRequestAuthServer(profile.getUsername(), newPassword, token));
        try(OutputStream outputStream = connection.getOutputStream()){
            byte[] sendData = data.getBytes(StandardCharsets.UTF_8);
            outputStream.write(sendData, 0, sendData.length);
            if (connection.getResponseCode() != 200) throw new AuthServerError("Some error with auth server");
        } catch (Exception exception){
            logger.error(exception.getMessage());
            throw new AuthServerError("Some error with auth server");
        }
        logger.info(connection.getResponseCode());



    }

    public String checkToken(String accessToken) throws IOException, BadTokenException {
        URL url = new URL("http://" + authServer + "/api/auth/checktoken");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
        connection.setRequestProperty("Access-Token", accessToken);

        if (connection.getResponseCode() != 200){
            connection.disconnect();
            throw new BadTokenException("Token is incorrect");
        }

        StringBuffer buffer = new StringBuffer();
        String newLine = System.getProperty("line.separator");
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        for (String line; (line = reader.readLine()) != null; ){
            if (buffer.length() > 0) buffer.append(newLine);
            buffer.append(line);
        }
        JsonObject object = JsonParser.parseString(buffer.toString()).getAsJsonObject();
        connection.disconnect();

        return object.get("id").getAsString();
    }


    private String registerProfile(RegistrationDTO registrationDTO) throws IOException {
        logger.info(authServer);
        URL url = new URL("http://" + authServer + "/api/auth/register");
        logger.info(url.toString());
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type","application/json");
        connection.setRequestProperty("Accept", "application/json");
        connection.setDoOutput(true);
        String data = new UsernamePasswordRegistrationDTO(registrationDTO).toJsonString();
        try(OutputStream outputStream = connection.getOutputStream()){
            byte[] bytes = data.getBytes(StandardCharsets.UTF_8);
            outputStream.write(bytes, 0, bytes.length);
            logger.info(connection.getResponseCode());
        } catch (Exception exception){
            exception.printStackTrace();
        }
        String newLine = System.getProperty("line.separator");
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));
        StringBuilder result = new StringBuilder();
        for (String line; (line = reader.readLine()) != null; ) {
            if (result.length() > 0) {
                result.append(newLine);
            }
            result.append(line);
        }
        connection.disconnect();
        String res = result.toString();
        JsonObject jsonObject = JsonParser.parseString(res).getAsJsonObject();
        return jsonObject.get("id").getAsString();
    }

}
