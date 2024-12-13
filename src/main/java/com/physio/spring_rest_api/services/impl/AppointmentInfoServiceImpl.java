package com.physio.spring_rest_api.services.impl;

import com.physio.spring_rest_api.dto.AppointmentDTO;
import com.physio.spring_rest_api.dto.AppointmentInfoDTO;
import com.physio.spring_rest_api.entities.Appointments;
import com.physio.spring_rest_api.entities.AppointmentsInfo;
import com.physio.spring_rest_api.exceptions.AlreadyExistsException;
import com.physio.spring_rest_api.exceptions.ResourceNotFoundException;
import com.physio.spring_rest_api.repositories.AppointmentsInfoRepo;
import com.physio.spring_rest_api.repositories.AppointmentsRepo;
import com.physio.spring_rest_api.services.AppointmentInfoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AppointmentInfoServiceImpl implements AppointmentInfoService {

    @Autowired
    AppointmentsInfoRepo repo;

    @Autowired
    AppointmentsRepo appointmentsRepo;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public AppointmentInfoDTO processAppointment(AppointmentInfoDTO dto, Appointments savedAppointment) {

      AppointmentsInfo savedInfo = this.repo.save(dtoTOEntity(dto));
      savedAppointment.setStatus(1);
      appointmentsRepo.save(savedAppointment);
      return entityTODto(savedInfo);
    }

    @Override
    public AppointmentInfoDTO getAppointmentInfo(UUID appointmentId) {
        if (this.repo.existsByAppointmentId(appointmentId)){
            return entityTODto(this.repo.findByAppointmentId(appointmentId));
        }else {
            throw new ResourceNotFoundException("AppointmentInfo", "Appointment Id", appointmentId);
        }
    }

    @Override
    public Appointments getAppointmentByID(UUID appointmentId) {
        Appointments appointment =  appointmentsRepo.findById(appointmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment", "AppointmentId", appointmentId));

        if (this.repo.existsByAppointmentId(appointmentId)){
            throw  new AlreadyExistsException("Appointment is already processed");
        }

        return appointment;
    }

//    @Override
//    public AppointmentInfoDTO processAppointmentWithFiles(AppointmentInfoDTO dto) {
//
//        Appointments appointment =  appointmentsRepo.findById(dto.getAppointmentId())
//                .orElseThrow(() -> new ResourceNotFoundException("Appointment", "AppointmentId", dto.getAppointmentId()));
//
//
//
//    }


    private AppointmentsInfo dtoTOEntity(AppointmentInfoDTO dto){
        return modelMapper.map(dto, AppointmentsInfo.class);
    }

    private AppointmentInfoDTO entityTODto(AppointmentsInfo entity){
        return modelMapper.map(entity, AppointmentInfoDTO.class);
    }

}
