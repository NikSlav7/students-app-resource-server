package com.example.ResourceServer.pdf;

import org.apache.pdfbox.contentstream.operator.Operator;
import org.apache.pdfbox.cos.COSBase;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;

import java.io.IOException;
import java.util.List;

public class CustomStripper extends PDFTextStripper {

    private float minX = -1f, maxX = -1f;
    boolean needToDelete = true;
    public CustomStripper() throws IOException {
    }

    @Override
    protected void writeString(String text, List<TextPosition> textPositions) throws IOException {
        if (text.trim().equalsIgnoreCase("Aasta/Kursus")) minX = textPositions.get(0).getX();
        if (text.trim().equalsIgnoreCase("Kursuse Hinded")) maxX = textPositions.get(0).getX() - 2f;
        if (!needToDelete && (text.trim().equalsIgnoreCase("Märkmised") || text.trim().equalsIgnoreCase("Selgitus"))) needToDelete = false;



        if (minX > 0 && maxX > 0 && needToDelete &&textPositions.get(0).getX() >= minX && textPositions.get(textPositions.size()-1).getX() <= maxX) return;
        super.writeString(text, textPositions);
    }

    @Override
    public String getText(PDDocument doc) throws IOException {
        return super.getText(doc);
    }


}
