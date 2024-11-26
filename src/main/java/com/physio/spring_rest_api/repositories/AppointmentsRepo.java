package com.physio.spring_rest_api.repositories;

import com.physio.spring_rest_api.entities.Appointments;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AppointmentsRepo extends JpaRepository<Appointments, UUID> {

    public List<Appointments> findByPatientId(UUID patientId);

}
