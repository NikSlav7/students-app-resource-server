package com.example.ResourceServer.domains;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Table;
import java.util.*;
import java.util.UUID;

@Table("subjects")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Subject {

    @Id
    private String subjectId;

    private String subjectName;

    @ManyToOne(cascade = CascadeType.ALL)
    private Period period;


    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL)
    private List<Mark> markList;
}
