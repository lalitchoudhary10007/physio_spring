package com.physio.spring_rest_api.repositories;

import com.physio.spring_rest_api.dto.AppointmentInfoDTO;
import com.physio.spring_rest_api.entities.AppointmentsInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AppointmentsInfoRepo extends JpaRepository<AppointmentsInfo, UUID> {

    AppointmentsInfo findByAppointmentId(UUID appointmentId);

    boolean existsByAppointmentId(UUID appointmentId);

}
