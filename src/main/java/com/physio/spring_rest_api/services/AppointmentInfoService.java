package com.physio.spring_rest_api.services;

import com.physio.spring_rest_api.dto.AppointmentDTO;
import com.physio.spring_rest_api.dto.AppointmentInfoDTO;
import com.physio.spring_rest_api.entities.Appointments;

import java.util.UUID;

public interface AppointmentInfoService {

    AppointmentInfoDTO processAppointment(AppointmentInfoDTO dto, Appointments savedAppointment);

    AppointmentInfoDTO getAppointmentInfo(UUID appointmentId);

    Appointments getAppointmentByID(UUID appointmentId);

    //AppointmentInfoDTO processAppointmentWithFiles(AppointmentInfoDTO dto);

}
