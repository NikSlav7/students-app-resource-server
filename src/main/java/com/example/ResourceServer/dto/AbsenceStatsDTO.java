package com.example.ResourceServer.dto;


import com.example.ResourceServer.domains.AbsenceStats;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonPropertyOrder({"Total Absent", "Absent with no reason", "Total Late", "Late with no reason"})
public class AbsenceStatsDTO {
    @JsonProperty("Total Absent")
    private Integer totalAbsent;
    @JsonProperty("Absent without a reason")
    private Integer noReasonAbsent;
    @JsonProperty("Total Late")
    private Integer totalLate;
    @JsonProperty("Late without a reason")
    private Integer noReasonLate;


    public AbsenceStatsDTO(AbsenceStats absenceStats){
        this.totalAbsent = absenceStats.getTotalAbsent();
        this.noReasonAbsent = absenceStats.getNoReasonAbsent();
        this.noReasonLate = absenceStats.getNoReasonLate();
        this.totalLate = absenceStats.getTotalLate();
    }
}
