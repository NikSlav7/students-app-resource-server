package com.example.ResourceServer.repositories;

import com.example.ResourceServer.domains.Profile;
import com.example.ResourceServer.domains.Year;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface YearsRepository extends JpaRepository<Year, String> {
    Optional<Year> getYearByYearNameAndProfile(String yearName, Profile profile);
}
