package com.physio.spring_rest_api.services.impl;

import com.physio.spring_rest_api.dto.AppointmentDTO;
import com.physio.spring_rest_api.entities.Appointments;
import com.physio.spring_rest_api.repositories.AppointmentsRepo;
import com.physio.spring_rest_api.services.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    AppointmentsRepo repo;

    @Override
    public AppointmentDTO createAppointment(AppointmentDTO appointmentDTO) {
        return this.entityTODto(this.repo.save(this.dtoTOEntity(appointmentDTO)));
    }

    @Override
    public List<AppointmentDTO> getAllAppointments() {
        List<Appointments> appointments = this.repo.findAll();
        List<AppointmentDTO> list = appointments.stream().map(this::entityTODto).toList();
        return list;
    }

    @Override
    public List<AppointmentDTO> getAllPatientAppointments(UUID patientId) {
        List<Appointments> appointments = this.repo.findByPatientId(patientId);
        List<AppointmentDTO> list = appointments.stream().map(this::entityTODto).toList();
        return list;
    }

    private Appointments dtoTOEntity(AppointmentDTO dto){
        Appointments entity = new Appointments();
        entity.setAppointmentDate(dto.getAppointmentDate());
        entity.setCreatedAt(dto.getCreatedAt());
        entity.setAppointmentTime(dto.getAppointmentTime());
        entity.setComplaint(dto.getComplaint());
        entity.setComments(dto.getComments());
        entity.setPatientId(dto.getPatientId());
        entity.setStatus(dto.getStatus());
        return entity;
    }

    private AppointmentDTO entityTODto(Appointments entity){
        AppointmentDTO dto = new AppointmentDTO();
        dto.setId(entity.getId());
        dto.setAppointmentDate(entity.getAppointmentDate());
        dto.setAppointmentTime(entity.getAppointmentTime());
        dto.setComments(entity.getComments());
        dto.setComments(entity.getComplaint());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setPatientId(entity.getPatientId());
        dto.setStatus(entity.getStatus());
        return dto;
    }


}
