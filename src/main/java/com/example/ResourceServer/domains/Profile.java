package com.example.ResourceServer.domains;

import com.example.ResourceServer.dto.RegistrationDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Table;
import java.util.*;
import java.util.UUID;

@Entity
@Table(name = "profiles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Profile {
    @Id
    private String profileId;
    private String email, username, firstName, secondName;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "profile")
    private List<Year> yearList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "profile")
    private List<Upload> uploadList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "profile")
    private List<PasswordResetToken> passwordResetTokenList;




    public Profile(RegistrationDTO registrationDTO){
        this.email = registrationDTO.getEmail();
        this.username = registrationDTO.getUsername();
        this.firstName = registrationDTO.getFirstName();
        this.secondName = registrationDTO.getSecondName();
    }


    public Profile generateId(){
        this.profileId = UUID.randomUUID().toString();
        return this;
    }

}

