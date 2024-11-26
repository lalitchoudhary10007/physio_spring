package com.physio.spring_rest_api.services.impl;

import com.physio.spring_rest_api.dto.PatientDTO;
import com.physio.spring_rest_api.entities.PatientsEntity;
import com.physio.spring_rest_api.exceptions.AlreadyExistsException;
import com.physio.spring_rest_api.exceptions.ResourceNotFoundException;
import com.physio.spring_rest_api.repositories.PatientRepo;
import com.physio.spring_rest_api.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientRepo repo;

    @Override
    public PatientDTO createPatient(PatientDTO patientDTO) {
        if (this.repo.existsByMobileOrEmail(patientDTO.getMobile(), patientDTO.getEmail())){
            throw new AlreadyExistsException("Mobile or Email already Exist");
        }else {
            return this.entityTODto(this.repo.save(this.dtoTOEntity(patientDTO)));
        }
    }

    @Override
    public PatientDTO getPatientById(UUID patientId) {
        PatientsEntity entity = this.repo.findById(patientId).orElseThrow(() -> new ResourceNotFoundException("Patient", "Id", patientId));
        return this.entityTODto(entity);
    }

    @Override
    public List<PatientDTO> getAllPatients() {
        List<PatientsEntity> patients = this.repo.findAll();
//        patients.stream().map(e -> {
//            return this.entityTODto(e);
//        }).toList();
        List<PatientDTO> list = patients.stream().map(this::entityTODto).toList();
        return list;
    }


    private PatientsEntity dtoTOEntity(PatientDTO dto){
        PatientsEntity entity = new PatientsEntity();
        entity.setName(dto.getName());
        entity.setAge(dto.getAge());
        entity.setGender(dto.getGender());
        entity.setEmail(dto.getEmail());
        entity.setAddress(dto.getAddress());
        entity.setMobile(dto.getMobile());
        entity.setCreatedAt(dto.getCreatedAt());
        return entity;
    }

    private PatientDTO entityTODto(PatientsEntity entity){
        PatientDTO dto = new PatientDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setAge(entity.getAge());
        dto.setGender(entity.getGender());
        dto.setEmail(entity.getEmail());
        dto.setAddress(entity.getAddress());
        dto.setMobile(entity.getMobile());
        dto.setCreatedAt(entity.getCreatedAt());
        return dto;
    }

}
