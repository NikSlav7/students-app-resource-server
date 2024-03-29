package com.example.ResourceServer.domains;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Table;

import java.util.*;

@Entity
@Table("uploads")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Upload {

    @Id
    private String uploadId;


    @Temporal(TemporalType.TIMESTAMP)
    private Date uploadDate;


    @OneToMany(mappedBy = "upload", cascade = CascadeType.ALL)
    private List<Mark> markList;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Profile profile;
}
