package com.example.ResourceServer.domains;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

@Table
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Period {

    @Id
    private String periodId;
    private String periodName;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Year year;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "period")
    private List<Subject> subjectList;

}
