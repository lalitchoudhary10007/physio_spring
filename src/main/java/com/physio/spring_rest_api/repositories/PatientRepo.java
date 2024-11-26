package com.physio.spring_rest_api.repositories;

import com.physio.spring_rest_api.entities.PatientsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PatientRepo extends JpaRepository<PatientsEntity, UUID> {

    boolean existsByMobileOrEmail(String mobile, String email);

}
