package com.example.ResourceServer.schedule;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@EnableScheduling
@Service
public class PasswordResetRequestsRemover {


    @Autowired
    JdbcTemplate jdbcTemplate;

    @Scheduled(fixedDelay = 1000*60*60*20)
    public void deleteOldRequests(){
        jdbcTemplate.update("DELETE FROM  password_reset_token WHERE expires_at < NOW()");
    }
}
