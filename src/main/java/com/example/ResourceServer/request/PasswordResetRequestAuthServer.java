package com.example.ResourceServer.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PasswordResetRequestAuthServer {

    @JsonProperty("username")
    String username;

    @JsonProperty("newPassword")
    String newPassword;
}
