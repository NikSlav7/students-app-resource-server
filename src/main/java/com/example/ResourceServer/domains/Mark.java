package com.example.ResourceServer.domains;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Table;

import java.util.*;
import java.util.UUID;

@Entity
@Table(name = "marks")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Mark{

    @Id
    private String markId;

    @Convert(converter = MarksConverter.class)
    private List<Double> markList;

    private double markAvg;


    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Subject subject;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Upload upload;

}
