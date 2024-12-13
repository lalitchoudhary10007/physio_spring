package com.physio.spring_rest_api.controllers;


import com.physio.spring_rest_api.dto.response.ApiResponse;
import com.physio.spring_rest_api.dto.AppointmentDTO;
import com.physio.spring_rest_api.services.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    @Autowired
    AppointmentService service;

    @PostMapping("/")
    public ResponseEntity<ApiResponse<AppointmentDTO>> createAppointment(@RequestBody AppointmentDTO dto){
        dto.setCreatedAt(LocalDateTime.now());
        AppointmentDTO createdDto = service.createAppointment(dto);
        return new ResponseEntity<>(
                new ApiResponse<>(
                        true,
                        "Appointment Created Successfully",
                        HttpStatus.CREATED.value(),
                        createdDto),
                HttpStatus.CREATED);
       // return new ResponseEntity<>(createdDto, HttpStatus.CREATED);
    }

    @GetMapping(path = "/")
    public ResponseEntity<List<AppointmentDTO>> getAppointments()
    {
        return new ResponseEntity<>(service.getAllAppointments(), HttpStatus.OK);

    }

    @GetMapping(path = "/{patientId}")
    public ResponseEntity<ApiResponse<List<AppointmentDTO>>> getAppointments(@PathVariable("patientId") UUID id){
        return new ResponseEntity<>(
                new ApiResponse<>(
                        true,
                        "Success",
                        HttpStatus.OK.value(),
                        service.getAllPatientAppointments(id)),
                HttpStatus.OK);
        //return new ResponseEntity<>(service.getAllPatientAppointments(id), HttpStatus.OK);
    }


}
