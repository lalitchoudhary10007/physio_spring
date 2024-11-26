package com.physio.spring_rest_api.controllers;


import com.physio.spring_rest_api.dto.ErrorResponse;
import com.physio.spring_rest_api.dto.PatientDTO;
import com.physio.spring_rest_api.exceptions.AlreadyExistsException;
import com.physio.spring_rest_api.services.PatientService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    @Autowired
    private PatientService service;


    @PostMapping("/")
    public ResponseEntity<PatientDTO> createPatient(@RequestBody PatientDTO dto){
        dto.setCreatedAt(LocalDateTime.now());
        PatientDTO createdDto = service.createPatient(dto);
        return new ResponseEntity<>(createdDto, HttpStatus.CREATED);
    }

    @ExceptionHandler(value = AlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleAlreadyExistsException(AlreadyExistsException ex) {
        return new ErrorResponse(HttpStatus.CONFLICT.value(), ex.getMessage());
    }

    //get with no params
    @GetMapping(path = "/")
    public ResponseEntity<List<PatientDTO>> getPatients()
    {
        return new ResponseEntity<>(service.getAllPatients(), HttpStatus.OK);

    }

    //get with path variable...it's mandatory
    @GetMapping(path = "/patient/{id}")
    public String getPatientById(@PathVariable("id") Long id){
          return "Hi "+id + " patient";
    }

    //get with path params optional
    //http://localhost:8080/patient?sortBy=createddate&limit=20
    @GetMapping(path = "/patient")
    public String getPatientsBy(@PathParam("sortBy") String sortBy,
                                @PathParam("limit") String limit){
         return "Hi Patients By" + sortBy + "Limit" + limit;
    }

    

}
