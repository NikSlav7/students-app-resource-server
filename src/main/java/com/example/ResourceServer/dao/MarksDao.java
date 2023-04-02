package com.example.ResourceServer.dao;


import com.example.ResourceServer.domains.Mark;
import com.example.ResourceServer.domains.Profile;
import com.example.ResourceServer.domains.Subject;
import com.example.ResourceServer.domains.Upload;
import com.example.ResourceServer.repositories.MarksRepository;
import com.example.ResourceServer.repositories.ProfilesRepository;
import com.example.ResourceServer.repositories.SubjectsRepository;
import jakarta.transaction.Transactional;
import org.hibernate.engine.transaction.jta.platform.internal.SunOneJtaPlatform;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class MarksDao {

    private final ProfilesRepository profilesRepository;

    private final MarksRepository marksRepository;

    private final SubjectsRepository subjectsRepository;


    public MarksDao(ProfilesRepository profilesRepository, MarksRepository marksRepository, SubjectsRepository subjectsRepository) {
        this.profilesRepository = profilesRepository;
        this.marksRepository = marksRepository;
        this.subjectsRepository = subjectsRepository;
    }


//    @Transactional
//    public void saveMarks(List<Subject> subjectList, Profile profile){
////        for (Subject subject : subjectList){
////            List<Subject> temp = new ArrayList<>(profile.getSubjectList()).stream().filter(sbj -> sbj.getSubjectName().equals(subject.getSubjectName())).collect(Collectors.toList());
////            if (temp.isEmpty()){
////                profile.getSubjectList().add(subject);
////            }
////            else {
////                Subject saved = temp.get(0);
////                subject.getMarkList().get(0).setSubject(saved);
////                saved.getMarkList().add(subject.getMarkList().get(0));
////            }
////        }
////        profilesRepository.save(profile);
//    }
}
