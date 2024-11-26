package com.physio.spring_rest_api.services;

import com.physio.spring_rest_api.dto.AppointmentDTO;

import java.util.List;
import java.util.UUID;

public interface AppointmentService {

    AppointmentDTO createAppointment(AppointmentDTO appointmentDTO);

    List<AppointmentDTO> getAllAppointments();

    List<AppointmentDTO> getAllPatientAppointments(UUID patientId);

}
