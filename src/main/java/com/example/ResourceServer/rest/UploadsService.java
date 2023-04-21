package com.example.ResourceServer.rest;


import com.example.ResourceServer.dao.ProfilesDao;
import com.example.ResourceServer.dao.UploadsDao;
import com.example.ResourceServer.domains.Profile;
import com.example.ResourceServer.domains.UploadWithAvg;
import com.example.ResourceServer.dto.UploadDeleteDTO;
import com.example.ResourceServer.exceptions.UserNotFoundException;
import com.example.ResourceServer.exceptions.WrongDataSentException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RequestMapping("/api/uploads")
@RestController
@Service
public class UploadsService {

    private final UploadsDao uploadsDao;

    private final ProfilesDao profilesDao;

    public UploadsService(UploadsDao uploadsDao, ProfilesDao profilesDao) {
        this.uploadsDao = uploadsDao;
        this.profilesDao = profilesDao;
    }

    @GetMapping("/get-uploads")
    public List<UploadWithAvg> getUploads(@RequestParam int limit, @RequestParam int offset){
        String id = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return uploadsDao.getUploads(id, limit, offset);
    }

    @PostMapping("/delete-upload")
    public ResponseEntity deleteUpload(@RequestBody @Validated UploadDeleteDTO uploadDeleteDTO) throws UserNotFoundException, WrongDataSentException {
        Profile profile = profilesDao.getProfileById(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        uploadsDao.deleteUpload(uploadDeleteDTO.getUploadId(), profile);
        return ResponseEntity.ok(uploadDeleteDTO.getUploadId());
    }
}
