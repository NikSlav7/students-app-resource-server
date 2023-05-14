package com.example.ResourceServer.dictionary;

public class LanguageDetector {


    public static Language detectFileLang(String text){
        if (text.contains("Kokku puudumisi")) return Language.ESTONIAN;
        else if (text.contains("Всего пропусков")) return Language.RUSSIAN;
        else return Language.ENGLISH;
    }
}
