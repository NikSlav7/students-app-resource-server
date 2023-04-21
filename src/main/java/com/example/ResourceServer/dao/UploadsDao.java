package com.example.ResourceServer.dao;


import com.example.ResourceServer.domains.Profile;
import com.example.ResourceServer.domains.Upload;
import com.example.ResourceServer.domains.UploadWithAvg;
import com.example.ResourceServer.exceptions.WrongDataSentException;
import com.example.ResourceServer.repositories.UploadsRepository;
import java.util.*;

import com.example.ResourceServer.rowMappers.UploadWithAvgMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UploadsDao {

    private final UploadsRepository uploadsRepository;

    private final JdbcTemplate jdbcTemplate;

    public UploadsDao(UploadsRepository uploadsRepository, JdbcTemplate jdbcTemplate) {
        this.uploadsRepository = uploadsRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    public Upload saveUpload(Upload upload){return uploadsRepository.save(upload);}


    public void deleteUpload(String uploadId, Profile profile) throws WrongDataSentException {
        uploadsRepository.delete(uploadsRepository.getUploadByUploadIdAndProfile(uploadId, profile).orElseThrow(() -> new WrongDataSentException("No Upload with such id existing")));
    }
    public List<UploadWithAvg> getUploads(String profileId, int limit, int offset){
        List<UploadWithAvg> uploads =
                jdbcTemplate.query("SELECT  * FROM upload WHERE profile_profile_id = ? ORDER BY upload_date OFFSET ? LIMIT ?", new UploadWithAvgMapper(jdbcTemplate), profileId, offset, limit);
        return uploads;
    }
}
