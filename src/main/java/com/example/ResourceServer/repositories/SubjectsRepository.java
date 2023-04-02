package com.example.ResourceServer.repositories;

import com.example.ResourceServer.domains.Profile;
import com.example.ResourceServer.domains.Subject;
import org.hibernate.metamodel.model.convert.spi.JpaAttributeConverter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SubjectsRepository extends JpaRepository<Subject, String> {
    Optional<Subject> getSubjectBySubjectId(String subjectId);

}
