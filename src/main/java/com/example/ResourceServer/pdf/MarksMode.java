package com.example.ResourceServer.pdf;

import com.example.ResourceServer.domains.Mark;

public enum MarksMode {
    MODE_INTEGER("int"), MODE_DOUBLE("double");

    String modeName;
    MarksMode(String s) {
        modeName = s;
    }

    public static MarksMode getModeByModeName(String modeName){
        for (MarksMode marksMode : MarksMode.values()){
            if (marksMode.modeName.equalsIgnoreCase(modeName)) return marksMode;
        }
        return MODE_INTEGER;
    }
}
