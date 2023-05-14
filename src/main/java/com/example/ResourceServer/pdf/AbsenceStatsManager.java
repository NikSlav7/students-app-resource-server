package com.example.ResourceServer.pdf;

import com.example.ResourceServer.dao.AbsenceDAO;
import com.example.ResourceServer.dao.PeriodsDao;
import com.example.ResourceServer.dictionary.Dictionary;
import com.example.ResourceServer.dictionary.Language;
import com.example.ResourceServer.domains.AbsenceStats;
import com.example.ResourceServer.domains.Year;
import org.springframework.stereotype.Component;

import java.util.UUID;




@Component
public class AbsenceStatsManager {

    private final AbsenceDAO absenceDAO;

    public AbsenceStatsManager(AbsenceDAO absenceDAO) {
        this.absenceDAO = absenceDAO;
    }

    public  AbsenceStats getAbsentStats(String pdfString, Year year, String periodName, Language language){
        String shortened = pdfString.split(Dictionary.translate("Kokku puudumisi", language))[1].split(Dictionary.translate("Kursuse nimetus", language))[0];
        String[] strings = shortened.split("\\s+");
        AbsenceStats absenceStats;
        try{
            absenceStats = absenceDAO.getOrCreateNew(year, periodName);
        } catch (Exception exception){
            absenceStats = new AbsenceStats();
            absenceStats.setAbsenceStatsId(UUID.randomUUID().toString());
        }
        int num = 0;
        for (String string : strings){
            try {
                int i = Integer.parseInt(string);
                switch (num){
                    case 0:
                        absenceStats.setTotalAbsent(i);
                        break;
                    case 1:
                        absenceStats.setNoReasonAbsent(i);
                        break;
                    case 2:
                        absenceStats.setTotalLate(i);
                        break;
                    case 3:
                        absenceStats.setNoReasonLate(i);
                        break;
                }
                num++;
            } catch (Exception e){
                continue;
            }
        }
        return absenceStats;
    }
}
