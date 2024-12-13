package com.physio.spring_rest_api.services.impl;

import com.physio.spring_rest_api.dto.AppointmentDTO;
import com.physio.spring_rest_api.entities.Appointments;
import com.physio.spring_rest_api.repositories.AppointmentsRepo;
import com.physio.spring_rest_api.services.AppointmentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    AppointmentsRepo repo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public AppointmentDTO createAppointment(AppointmentDTO appointmentDTO) {
        return this.entityTODto(this.repo.save(this.dtoTOEntity(appointmentDTO)));
    }

    @Override
    public List<AppointmentDTO> getAllAppointments() {
        List<Appointments> appointments = this.repo.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
        List<AppointmentDTO> list = appointments.stream().map(this::entityTODto).toList();
        return list;
    }

    @Override
    public List<AppointmentDTO> getAllPatientAppointments(UUID patientId) {
        List<Appointments> appointments = this.repo.findByPatientId(patientId, Sort.by(Sort.Direction.DESC, "createdAt"));
        List<AppointmentDTO> list = appointments.stream().map(this::entityTODto).toList();
        return list;
    }

    private Appointments dtoTOEntity(AppointmentDTO dto){
        return modelMapper.map(dto, Appointments.class);
    }

    private AppointmentDTO entityTODto(Appointments entity){
        return modelMapper.map(entity, AppointmentDTO.class);
    }


}
