package com.example.ResourceServer.dictionary;
import java.util.*;

public class Dictionary {

    public static Map<String, Map<Language, String>> dictionary;



    public static String translate(String word, Language toLang){
        if (dictionary == null) init();
        if (toLang == Language.ESTONIAN) return word;
        return dictionary.get(word).get(toLang);
    }

    public static void init(){
        dictionary = new HashMap<>();
        String[] estWords = new String[]{"Kokku puudumisi", "Kursuse nimetus", "Selgitus", "Märkamised", "Aasta/Kursus", "Kursuse hinded", "Periood"};
        String[] rusWords = new String[]{"Всего пропусков", "Название курса", "Пояснения", "Замечания", "Годовые/Курсовые", "Оценки за курс", "Период"};
        String[] engWords = new String[]{"kokku puudumisi", "kursuse nimetus", "selgitus", "märkmised", "aasta/kursus", "kursuse hinded", "periood"};
        String[][] arrays = new String[][]{rusWords, engWords};
        Language[] languages = new Language[]{Language.RUSSIAN, Language.ENGLISH};
        for (int i = 0; i < estWords.length; i++){
            String word = estWords[i];
            dictionary.put(word, new HashMap<>());
            for (int j = 0; j < arrays.length; j++) dictionary.get(word).put(languages[j], arrays[j][i]);
        }
    }


}
