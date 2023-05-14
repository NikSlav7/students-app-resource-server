package com.example.ResourceServer.dictionary;

public enum Language {
    ESTONIAN("ee"),
    RUSSIAN("ru"),
    ENGLISH("en");

    String code;
    Language(String code) {
        this.code = code;
    }

    public static Language getLanguageByCode(String code){
        for (Language lang : Language.values())
            if (lang.code.equalsIgnoreCase(code)) return lang;
        return null;
    }
}
