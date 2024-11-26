package com.physio.spring_rest_api.services;

import com.physio.spring_rest_api.dto.PatientDTO;
import com.physio.spring_rest_api.entities.PatientsEntity;

import java.util.List;
import java.util.UUID;

public interface PatientService {


    PatientDTO createPatient(PatientDTO patientDTO);

    PatientDTO getPatientById(UUID patientId);

    List<PatientDTO> getAllPatients();

}
