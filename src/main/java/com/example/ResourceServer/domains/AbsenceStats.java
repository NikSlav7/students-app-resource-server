package com.example.ResourceServer.domains;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.MapKeyMutability;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table
@Entity
public class AbsenceStats {

    @Id
    private String absenceStatsId;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
    @JoinColumn
    private Period period;


    private Integer totalAbsent;

    private Integer noReasonAbsent;

    private Integer totalLate;

    private Integer noReasonLate;

}
