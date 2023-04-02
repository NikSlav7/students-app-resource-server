package com.example.ResourceServer.pdf;


import com.example.ResourceServer.dao.UploadsDao;
import com.example.ResourceServer.domains.UploadWithAvg;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RequestMapping("/api/uploads")
@RestController
@Service
public class UploadsManager {

    private final UploadsDao uploadsDao;

    public UploadsManager(UploadsDao uploadsDao) {
        this.uploadsDao = uploadsDao;
    }

    @GetMapping("/get-uploads")
    public List<UploadWithAvg> getUploads(@RequestParam int limit, @RequestParam int offset){
        String id = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return uploadsDao.getUploads(id, limit, offset);
    }
}
