package com.example.ResourceServer.marktests;


import com.example.ResourceServer.TestVariables;
import com.example.ResourceServer.dao.AbsenceDAO;
import com.example.ResourceServer.dao.PeriodsDao;
import com.example.ResourceServer.dao.ProfilesDao;
import com.example.ResourceServer.dao.YearsDao;
import com.example.ResourceServer.dictionary.Language;
import com.example.ResourceServer.domains.AbsenceStats;
import com.example.ResourceServer.domains.Profile;
import com.example.ResourceServer.domains.Subject;
import com.example.ResourceServer.pdf.AbsenceStatsManager;
import com.example.ResourceServer.pdf.MarksManager;
import com.example.ResourceServer.pdf.MarksMode;
import com.example.ResourceServer.repositories.AbsenceStatsRepository;
import com.example.ResourceServer.repositories.PeriodRepository;
import com.example.ResourceServer.repositories.ProfilesRepository;
import com.example.ResourceServer.repositories.YearsRepository;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
public class MarkTests {


    private ProfilesDao profilesDao;


    @Mock
    private YearsRepository yearsRepository;
    @Mock
    private PeriodRepository periodRepository;

    @Mock
    private ProfilesRepository profilesRepository;

    @Mock
    private JdbcTemplate jdbcTemplate;

    @Mock
    private AbsenceStatsRepository absenceStatsRepository;


    private MarksManager marksManager;

    private AutoCloseable autoCloseable;

    private AbsenceStatsManager absenceStatsManager;



    @BeforeEach
    void setup(){
        autoCloseable = MockitoAnnotations.openMocks(this);
        YearsDao yearsDao = new YearsDao(yearsRepository, jdbcTemplate);
        PeriodsDao periodsDao = new PeriodsDao(periodRepository);
        AbsenceDAO absenceDAO = new AbsenceDAO(periodsDao, yearsDao, absenceStatsRepository);
        marksManager = new MarksManager(yearsDao, periodsDao, profilesRepository, absenceDAO);
        absenceStatsManager = new AbsenceStatsManager(absenceDAO);
    }

    @AfterEach
    void destroy() throws Exception {
        autoCloseable.close();
    }


//    @Test
//    void canCalculateDottedAvg() throws Exception{
//        String profileId = "banan";
//        Profile profile = new Profile();
//        profile.setProfileId(profileId);
//        profile.setYearList(new ArrayList<>());
//        profile.setUploadList(new ArrayList<>());
//
//        String testMarks = TestVariables.DOTTED_AJA;
//        String yearName = "2021";
//        AbsenceStats absenceStats = absenceStatsManager.getAbsentStats(testMarks, null, MarksManager.extractPeriod(testMarks), Language.ESTONIAN);
//        List<Subject> subjectList = marksManager.createAndSaveMarks(MarksManager.extractMarks(testMarks),absenceStats, profile, yearName, MarksManager.extractPeriod(testMarks), MarksMode.MODE_DOUBLE);
//        assertThat(subjectList.stream().filter(element -> element.getSubjectName().trim().equalsIgnoreCase("AJA")).collect(Collectors.toList()).size()).isNotZero();
//    }
}

