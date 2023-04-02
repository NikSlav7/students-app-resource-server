package com.example.ResourceServer;

import com.example.ResourceServer.security.AuthManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RequestMapping("/test")
@RestController
@Service
public class test {

    private final AuthManager authManager;

    @Autowired
    public test(AuthManager authManager) {
        this.authManager = authManager;
    }

    @GetMapping("/get")
    public String test(@RequestHeader("Access-Token") String accessToken) throws IOException {
       return "Hi";
    }
}
