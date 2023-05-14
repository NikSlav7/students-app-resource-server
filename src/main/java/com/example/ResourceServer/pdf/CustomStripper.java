package com.example.ResourceServer.pdf;

import ch.qos.logback.core.model.INamedModel;
import com.example.ResourceServer.dictionary.Dictionary;
import com.example.ResourceServer.dictionary.Language;
import com.example.ResourceServer.dictionary.LanguageDetector;
import org.apache.pdfbox.contentstream.operator.Operator;
import org.apache.pdfbox.cos.COSBase;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomStripper extends PDFTextStripper {
    private float minX = -1f, maxX = -1f;
    private float nameMinX = -1f, nameMaxX = -1f;
    boolean needToDelete = true;

    LastMark lastMark = null;
    boolean needMark = false;

    boolean stopReading = false;

    String lineSeparator = System.getProperty("line.separator");

    Language language;

    public CustomStripper() throws IOException {

    }

    @Override
    protected void writeString(String text, List<TextPosition> textPositions) throws IOException {
        super.writeString(text, textPositions);
//        detectLang(text);
//        if (text.trim().equalsIgnoreCase(Dictionary.translate("Selgitus", language)) || text.trim().equalsIgnoreCase(Dictionary.translate("Märkamised", language))) {
//            if (lastMark != null){
//                super.writeString(lastMark.getString().toString().replace(lineSeparator, "") + lineSeparator, lastMark.getTextPositionList());
//                lastMark = null;
//            }
//            needMark = false;
//        }
//        if (text.trim().equalsIgnoreCase(Dictionary.translate("Aasta/Kursus", language))) minX = textPositions.get(0).getX()-3f;
//        if (text.trim().equalsIgnoreCase(Dictionary.translate("Kursuse hinded", language))) maxX = textPositions.get(0).getX() - 2f;
//        if (!needToDelete && (text.trim().equalsIgnoreCase(Dictionary.translate("Märkamised", language)) || text.trim().equalsIgnoreCase(Dictionary.translate("Selgitus", language)))) needToDelete = false;
//        if (text.trim().equalsIgnoreCase(Dictionary.translate("Kursuse nimetus", language))) {
//            nameMinX = textPositions.get(0).getX();
//            nameMaxX = textPositions.get(textPositions.size() - 1).getX() + 2f;
//            needMark  = true;
//        }
//        if (minX > 0 && maxX > 0 && needToDelete &&textPositions.get(0).getX() >= minX && textPositions.get(textPositions.size()-1).getX() <= maxX) return;
//        if (needMark && textPositions.get(0).getX() > nameMinX && textPositions.get(0).getX() < nameMaxX){
//            LastMark mark = new LastMark();
//            mark.setString(new StringBuilder(text));
//            mark.setTextPositionList(textPositions);
//            if (lastMark != null){
//                super.writeString(lastMark.getString().toString().replace(lineSeparator, "") + lineSeparator, lastMark.getTextPositionList());
//                lastMark = mark;
//                return;
//            }
//            lastMark = mark;
//        }
//        else if (needMark && lastMark != null && textPositions.get(0).getX() >= nameMaxX){
//            lastMark.getString().append(text).append(" ");
//            lastMark.mergeList(textPositions);
//        }
//        if (!needMark || lastMark == null) super.writeString(text, textPositions);
    }

    @Override
    public String getText(PDDocument doc) throws IOException {
        return super.getText(doc);
    }

    private void detectLang(String text){
        if (language == null) language = LanguageDetector.detectFileLang(text);
    }

    public Language getLanguage(){
        return language;
    }



}
