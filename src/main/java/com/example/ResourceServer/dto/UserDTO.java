package com.example.ResourceServer.dto;


import com.example.ResourceServer.domains.Profile;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTO {
    String id, firstName, secondName;

    public UserDTO(Profile profile){
        this.id = profile.getProfileId();
        this.firstName = profile.getFirstName();
        this.secondName = profile.getSecondName();
    }
}
