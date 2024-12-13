package com.physio.spring_rest_api.services.impl;

import com.physio.spring_rest_api.dto.PatientDTO;
import com.physio.spring_rest_api.dto.response.ApiResponse;
import com.physio.spring_rest_api.dto.response.PaginationApiResponse;
import com.physio.spring_rest_api.entities.PatientsEntity;
import com.physio.spring_rest_api.exceptions.AlreadyExistsException;
import com.physio.spring_rest_api.exceptions.ResourceNotFoundException;
import com.physio.spring_rest_api.repositories.PatientRepo;
import com.physio.spring_rest_api.services.PatientService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientRepo repo;

    @Autowired
    private ModelMapper modelMapper;

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
    public PaginationApiResponse getAllPatients(int pageNumber, int pageSize) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<PatientsEntity> pagePatients = this.repo.findAll(pageable);

        //List<PatientsEntity> patients = this.repo.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
//        patients.stream().map(e -> {
//            return this.entityTODto(e);
//        }).toList();
        List<PatientDTO> list = pagePatients.getContent().stream().map(this::entityTODto).toList();
        PaginationApiResponse response = new PaginationApiResponse();
        response.setData(list);
        response.setPageNumber(pagePatients.getNumber());
        response.setPageSize(pagePatients.getSize());
        response.setTotalElements(pagePatients.getTotalElements());
        response.setTotalPages(pagePatients.getTotalPages());
        response.setLastPage(pagePatients.isLast());

        return response;
    }

    @Override
    public List<PatientDTO> searchPatients(String keyword) {
        List<PatientsEntity> patientsEntities = this.repo.findByMobileContaining(keyword);
        List<PatientDTO> data = patientsEntities.stream().map((p) -> modelMapper.map(p, PatientDTO.class)).collect(Collectors.toList());
        return data;
    }


    private PatientsEntity dtoTOEntity(PatientDTO dto){
        return modelMapper.map(dto, PatientsEntity.class);
    }

    private PatientDTO entityTODto(PatientsEntity entity){
        return modelMapper.map(entity, PatientDTO.class);
    }

}
