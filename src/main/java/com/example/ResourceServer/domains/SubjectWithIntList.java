package com.example.ResourceServer.domains;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.*;
import java.util.stream.Collectors;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SubjectWithIntList {

    private String subjectName;

    private List<SimplifiedMark> list;

    public SubjectWithIntList(Subject subject){
        this.subjectName = subject.getSubjectName();
        this.list = subject.getMarkList().stream().map(mark -> new SimplifiedMark(mark.getMarkAvg(), mark.getUpload().getUploadDate())).collect(Collectors.toList());
    }
}
