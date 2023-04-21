package com.example.ResourceServer.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PeriodDeleteDTO {

    @NonNull
    private String yearName, periodName;
}
