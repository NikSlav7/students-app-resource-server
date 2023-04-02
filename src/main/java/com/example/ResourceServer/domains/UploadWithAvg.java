package com.example.ResourceServer.domains;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UploadWithAvg {

    private String uploadId;
    private Date uploadDate;
    private Double overallAvg;
}
