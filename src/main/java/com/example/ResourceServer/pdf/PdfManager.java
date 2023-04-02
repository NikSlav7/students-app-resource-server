package com.example.ResourceServer.pdf;


import com.example.ResourceServer.dao.MarksDao;
import com.example.ResourceServer.dao.ProfilesDao;
import com.example.ResourceServer.dao.UploadsDao;
import com.example.ResourceServer.domains.Profile;
import com.example.ResourceServer.domains.Subject;
import com.example.ResourceServer.exceptions.UserNotFoundException;
import com.example.ResourceServer.exceptions.WrongFileException;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
@RestController
@RequestMapping("/api/pdf")
public class PdfManager {


    private final ProfilesDao profilesDao;

    private final UploadsDao uploadsDao;

    private final MarksDao marksDao;

    private final MarksManager marksManager;

    public PdfManager(ProfilesDao profilesDao, UploadsDao uploadsDao, MarksDao marksDao, MarksManager marksManager) {
        this.profilesDao = profilesDao;
        this.uploadsDao = uploadsDao;
        this.marksDao = marksDao;
        this.marksManager = marksManager;
    }


    @PostMapping(path = "/marks-sheet", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity sendMarksSheet(HttpServletRequest httpRequest) throws IOException, UserNotFoundException, WrongFileException {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) httpRequest;
        Set<String> fileNames = multipartRequest.getFileMap().keySet();
        File file = null;
        for (String key : fileNames){
            file = FilesManager.multipartToFile(multipartRequest.getFile(key));
        }
        PDDocument document = Loader.loadPDF(file);
        PDFTextStripper pdfTextStripper = new PDFTextStripper();
        String text = pdfTextStripper.getText(document);
        String marks = MarksManager.extractMarks(text);
        file.delete();
        Profile profile = profilesDao.getProfileById(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        System.out.println(marks);

        List<Subject> subjectList = marksManager.getAvg(marks, profile, "2022/2023", MarksManager.extractPeriod(text));
        return ResponseEntity.ok(marks);
    }
}
