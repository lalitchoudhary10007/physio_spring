package com.physio.spring_rest_api.controllers;


import com.physio.spring_rest_api.dto.response.ApiResponse;
import com.physio.spring_rest_api.dto.PatientDTO;
import com.physio.spring_rest_api.dto.response.PaginationApiResponse;
import com.physio.spring_rest_api.services.PatientService;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    @Autowired
    private PatientService service;


    @PostMapping("/")
    public ResponseEntity<ApiResponse<PatientDTO>> createPatient(@Valid @RequestBody PatientDTO dto) {
        dto.setCreatedAt(LocalDateTime.now());
        PatientDTO createdDto = service.createPatient(dto);
        // return new ResponseEntity<>(createdDto, HttpStatus.CREATED);

        return new ResponseEntity<>(
                new ApiResponse<>(
                        true,
                        "Patient Created Successfully",
                        HttpStatus.CREATED.value(),
                        createdDto),
                HttpStatus.CREATED);
    }


    //get with no params
    @GetMapping(path = "/")
    public ResponseEntity<PaginationApiResponse> getPatients(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "100", required = false) int pageSize
    ) {
        PaginationApiResponse apiResponse = service.getAllPatients(pageNumber, pageSize);
        apiResponse.setResponseStatus(true);
        apiResponse.setResponseMessage("Success");
        apiResponse.setResponseCode(HttpStatus.OK.value());
        return new ResponseEntity<>(
                apiResponse, HttpStatus.OK
        );
    }

    @GetMapping(path = "/search/{keywords}")
    public ResponseEntity<ApiResponse<List<PatientDTO>>> searchPatients(
            @PathVariable("keywords") String keywords
    ){
        return new ResponseEntity<>(
                new ApiResponse<>(
                        true,
                        "Success",
                        HttpStatus.OK.value(),
                        service.searchPatients(keywords)
                ),HttpStatus.OK
        );
    }

    //get with path variable...it's mandatory
    @GetMapping(path = "/{id}")
    public ResponseEntity<ApiResponse<PatientDTO>> getPatientById(@PathVariable("id") UUID patientId) {
        return new ResponseEntity<>(
                new ApiResponse<>(
                        true,
                        "Success",
                        HttpStatus.OK.value(),
                        service.getPatientById(patientId)),
                HttpStatus.OK);
    }

    //get with path params optional
    //http://localhost:8080/patient?sortBy=createddate&limit=20
    @GetMapping(path = "/patient")
    public String getPatientsBy(@PathParam("sortBy") String sortBy,
                                @PathParam("limit") String limit) {
        return "Hi Patients By" + sortBy + "Limit" + limit;
    }


}
