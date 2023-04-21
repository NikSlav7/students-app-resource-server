package com.example.ResourceServer.repositories;

import com.example.ResourceServer.domains.Profile;
import com.example.ResourceServer.domains.Upload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UploadsRepository extends JpaRepository<Upload, String> {
    Optional<Upload> getUploadByUploadIdAndProfile(String uploadId, Profile profile);
}
