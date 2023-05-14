package com.example.ResourceServer.pdf;

import com.example.ResourceServer.dao.AbsenceDAO;
import com.example.ResourceServer.dao.PeriodsDao;
import com.example.ResourceServer.dao.YearsDao;
import com.example.ResourceServer.dictionary.Dictionary;
import com.example.ResourceServer.dictionary.Language;
import com.example.ResourceServer.dictionary.LanguageDetector;
import com.example.ResourceServer.domains.*;
import com.example.ResourceServer.dto.AbsenceStatsDTO;
import com.example.ResourceServer.exceptions.WrongFileException;
import com.example.ResourceServer.repositories.ProfilesRepository;
import jakarta.transaction.Transactional;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.stereotype.Component;
import technology.tabula.ObjectExtractor;
import technology.tabula.Page;
import technology.tabula.PageIterator;
import technology.tabula.Table;
import technology.tabula.extractors.SpreadsheetExtractionAlgorithm;

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


    public static String extractPeriod(String pdfString, Language language){
        String lineSep = System.getProperty("line.separator");
        String[] strings = pdfString.split(lineSep);
        for (String s : strings){
            if (s.contains(Dictionary.translate("Periood", language) + ":")) return s.split("\\s+")[1];
        }
        return null;
    }

    public static String extractMarks(PDDocument document) throws WrongFileException {
        StringBuilder stringBuilder = new StringBuilder();
        ObjectExtractor objectExtractor = new ObjectExtractor(document);
        SpreadsheetExtractionAlgorithm spreadsheetExtractionAlgorithm = new SpreadsheetExtractionAlgorithm();
        PageIterator pageIterator = objectExtractor.extract();
        String lineSeparator = System.getProperty("line.separator");
        while (pageIterator.hasNext()){
            Page page = pageIterator.next();
            List<Table> list = spreadsheetExtractionAlgorithm.extract(page);
            if (list.size() == 0) continue;
            Table table = list.get(0);
            for (int i = 1; i < table.getRowCount(); i++){
                stringBuilder.append(table.getCell(i, 0).getText() + "," + table.getCell(i, 3).getText().replace(lineSeparator, "") + lineSeparator);
            }
        }
        return stringBuilder.toString();
    }

    @Transactional
    public List<Subject> createAndSaveMarks(String marksString, AbsenceStats absenceStats, Profile profile, String yearName, String periodName, MarksMode marksMode){
        String lineSeparator = System.getProperty("line.separator");

        Year year = yearsDao.getByNameOrCreateYear(yearName, profile);
        Period period = periodsDao.getByNameOrCreatePeriod(periodName, year);
        String[] subjects = marksString.split(lineSeparator + "|\n");
        List<Subject> markList = new ArrayList<>();
        Upload upload = new Upload();
        upload.setUploadId(UUID.randomUUID().toString());
        upload.setUploadDate(new Date());
        upload.setMarkList(new ArrayList<>());
        if (marksMode == MarksMode.MODE_INTEGER) {
            for (String subject : subjects) {
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

                for (int i = 1; i < marks.length; i++) {
                    if (marks[i].isBlank()) continue;
                    try {
                        sum += Double.parseDouble(marks[i]);
                        marksList.add(Double.parseDouble(marks[i]));
                        len++;
                    } catch (NumberFormatException exception) {
                        continue;
                    }
                }
                double avg = (double) sum / len;
                if (Double.isNaN(avg)) continue;
                Mark mark = new Mark(UUID.randomUUID().toString(), marksList, avg, sbj, upload);
                upload.setMarkList(List.of(mark));
                upload.setProfile(profile);
                sbj.setMarkList(List.of(mark));
                sbj.setPeriod(period);
                markList.add(sbj);
            }
        }
        else {
            for (String subject : subjects) {
                if (subject.isBlank()) continue;
                String name = subject.split(",")[0];
                subject = subject.replace(name, " ");
                String[] marks = subject.split(" ,");
                Subject sbj = new Subject();
                sbj.setSubjectName(name);
                sbj.setSubjectId(UUID.randomUUID().toString());
                List<Double> marksList = new ArrayList<>();

                double sum = 0, len = 0;

                for (int i = 0; i < marks.length; i++) {
                    String[] marks2 = marks[i].split("/|\\s+");
                    for (int j = 0; j < marks2.length; j++){
                        marks2[j] = marks2[j].replace(',', '.');
                        marks2[j] = marks2[j].replace('*', (char) 0);
//                        if (marks2[j].trim().charAt(marks2[j].trim().length()-1) == '*') marks2[j] = marks2[j].trim().substring(0, marks2[j].trim().length()-1);
                        if (marks2[j].isBlank()) continue;
                        try{
                            sum += Double.parseDouble(marks2[j]);
                            marksList.add(Double.parseDouble(marks2[j]));
                            len++;
                        } catch (Exception ex){
                            continue;
                        }
                    }
                }
                double avg = (double) sum / len;
                if (Double.isNaN(avg)) continue;
                Mark mark = new Mark(UUID.randomUUID().toString(), marksList, avg, sbj, upload);
                upload.setMarkList(List.of(mark));
                upload.setProfile(profile);
                sbj.setMarkList(List.of(mark));
                sbj.setPeriod(period);
                markList.add(sbj);
            }
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
