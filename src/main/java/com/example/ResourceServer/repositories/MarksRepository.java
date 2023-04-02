package com.example.ResourceServer.repositories;

import com.example.ResourceServer.domains.Mark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.lang.management.OperatingSystemMXBean;
import java.util.Optional;

@Repository
public interface MarksRepository extends JpaRepository<Mark, String> {
    Optional<Mark> getMarkByMarkId(String markId);

}
