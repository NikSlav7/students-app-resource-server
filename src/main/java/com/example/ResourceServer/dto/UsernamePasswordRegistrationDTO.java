package com.example.ResourceServer.dto;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UsernamePasswordRegistrationDTO {
    private String username, password;

    public UsernamePasswordRegistrationDTO(RegistrationDTO registrationDTO){
        this.password = registrationDTO.getPassword();
        this.username = registrationDTO.getUsername();
    }


    @Override
    public String toString() {
        return "UsernamePasswordRegistrationDTO{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String toJsonString(){
        return new Gson().toJson(this);
    }
}
