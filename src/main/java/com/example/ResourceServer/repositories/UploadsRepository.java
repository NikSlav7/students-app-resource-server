package com.example.ResourceServer.repositories;

import com.example.ResourceServer.domains.Upload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UploadsRepository extends JpaRepository<Upload, String> {
}
