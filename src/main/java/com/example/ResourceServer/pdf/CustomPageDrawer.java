package com.example.ResourceServer.pdf;

import org.apache.pdfbox.rendering.PageDrawer;
import org.apache.pdfbox.rendering.PageDrawerParameters;

import java.io.IOException;

public class CustomPageDrawer extends PageDrawer {
    public CustomPageDrawer(PageDrawerParameters parameters) throws IOException {
        super(parameters);
    }

    @Override
    public void strokePath() throws IOException {
        System.out.println(getLinePath());
    }
}
