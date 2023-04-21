package com.example.ResourceServer.pdf;

import com.example.ResourceServer.dao.AbsenceDAO;
import com.example.ResourceServer.dao.PeriodsDao;
import com.example.ResourceServer.dao.YearsDao;
import com.example.ResourceServer.domains.*;
import com.example.ResourceServer.dto.AbsenceStatsDTO;
import com.example.ResourceServer.exceptions.WrongFileException;
import com.example.ResourceServer.repositories.ProfilesRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;


@Component
public class MarksManager {


    private final YearsDao yearsDao;

    private final PeriodsDao periodsDao;

    private final ProfilesRepository profilesRepository;

    private final AbsenceDAO absenceStatsDAO;

    public MarksManager(YearsDao yearsDao, PeriodsDao periodsDao, ProfilesRepository profilesRepository, AbsenceDAO absenceStatsDAO) {
        this.yearsDao = yearsDao;
        this.periodsDao = periodsDao;
        this.profilesRepository = profilesRepository;
        this.absenceStatsDAO = absenceStatsDAO;
    }


    public static String extractPeriod(String pdfString){
        String lineSep = System.getProperty("line.separator");
        String[] strings = pdfString.split(lineSep);
        for (String s : strings){
            if (s.contains("Periood:")) return s.split("\\s+")[1];
        }
        return null;
    }

    public static String extractMarks(String pdfString) throws WrongFileException {
        if (!pdfString.contains("Kursuse hinded") || !pdfString.contains("Selgitus")  || !pdfString.contains("Märkamised")) throw new WrongFileException("Wrong file was sent");
        String[] first = pdfString.split("Kursuse hinded");
        String[] second = first[1].split("Selgitus");
        if (second.length == 1) second = second[0].split("Märkamised");
        return second[0];
    }

    @Transactional
    public List<Subject> createAndSaveMarks(String marksString, AbsenceStats absenceStats, Profile profile, String yearName, String periodName){
        String lineSeparator = System.getProperty("line.separator");

        Year year = yearsDao.getByNameOrCreateYear(yearName, profile);
        Period period = periodsDao.getByNameOrCreatePeriod(periodName, year);


        String[] subjects = marksString.split(lineSeparator + "|\n");
        List<Subject> markList = new ArrayList<>();
        Upload upload = new Upload();
        upload.setUploadId(UUID.randomUUID().toString());
        upload.setUploadDate(new Date());
        upload.setMarkList(new ArrayList<>());


        for (String subject : subjects){
            if (subject.isBlank()) continue;
            String[] marks = subject.split(",");
            String name = marks[0];
            subject = subject.replace(name, "");
            marks = subject.split(",|/|\\s+");

            Subject sbj = new Subject();
            sbj.setSubjectName(name);
            sbj.setSubjectId(UUID.randomUUID().toString());
            List<Double> marksList = new ArrayList<>();

            double sum = 0, len = 0;

            for (int i = 1; i < marks.length; i++){
                if (marks[i].isBlank()) continue;
                try {
                    sum += Double.parseDouble(marks[i]);
                    marksList.add(Double.parseDouble(marks[i]));
                    len++;
                } catch (NumberFormatException exception){
                    continue;
                }
            }
            double avg =(double) sum / len;
            if (Double.isNaN(avg)) continue;
            Mark mark = new Mark(UUID.randomUUID().toString(), marksList, avg, sbj, upload);
            upload.setMarkList(List.of(mark));
            upload.setProfile(profile);
            sbj.setMarkList(List.of(mark));
            sbj.setPeriod(period);
            markList.add(sbj);
        }
        profile.getUploadList().add(upload);



        for (Subject subject : markList){
            List<Subject> temp = new ArrayList<>(period.getSubjectList()).stream().filter(sbj -> sbj.getSubjectName().equals(subject.getSubjectName())).collect(Collectors.toList());
            if (temp.isEmpty()){
                period.getSubjectList().add(subject);
                subject.setPeriod(period);
            }
            else {
                Subject saved = temp.get(0);
                subject.getMarkList().get(0).setSubject(saved);
                saved.getMarkList().add(subject.getMarkList().get(0));
            }
        }
        period.setAbsenceStats(absenceStats);
        year.getPeriodList().add(period);
        year.setProfile(profile);
        period.setYear(year);
        profile.getYearList().add(year);
        absenceStats.setPeriod(period);
        profilesRepository.save(profile);

        return markList;
    }


}
