package com.example.ResourceServer.domains;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.*;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Year {

    @Id
    private String yearId;
    private String yearName;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "year")
    private List<Period> periodList;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Profile profile;
}
