package com.physio.spring_rest_api.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.physio.spring_rest_api.dto.AppointmentDTO;
import com.physio.spring_rest_api.dto.AppointmentInfoDTO;
import com.physio.spring_rest_api.dto.response.ApiResponse;
import com.physio.spring_rest_api.entities.Appointments;
import com.physio.spring_rest_api.services.AppointmentInfoService;
import com.physio.spring_rest_api.services.FileService;
import com.physio.spring_rest_api.utils.StringUtils;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.UUID;


@RestController
@RequestMapping("/api/appointments/info")
public class AppointmentInfoController {

    @Autowired
    AppointmentInfoService service;

    private final Logger logger = LoggerFactory.getLogger(AppointmentInfoController.class);

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;

//    @PostMapping("/")
//    public ResponseEntity<ApiResponse<AppointmentInfoDTO>> saveAppointmentInfo(@Valid @RequestBody AppointmentInfoDTO dto) {
//        dto.setCreatedAt(LocalDateTime.now());
//        AppointmentInfoDTO createdDto = service.processAppointment(dto);
//
//        return new ResponseEntity<>(
//                new ApiResponse<>(
//                        true,
//                        "Appointment Info Submitted Successfully",
//                        HttpStatus.CREATED.value(),
//                        createdDto),
//                HttpStatus.CREATED);
//    }

    @PostMapping("/")
    public ResponseEntity<?> saveAppointmentWithImages(
            @RequestParam(value = "appointment_info", required = true) String data,
            @RequestParam(value = "image_1", required = false) MultipartFile image1,
            @RequestParam(value = "image_2", required = false) MultipartFile image2,
            @RequestParam(value = "image_3", required = false) MultipartFile image3,
            @RequestParam(value = "image_4", required = false) MultipartFile image4,
            @RequestParam(value = "image_5", required = false) MultipartFile image5) throws IOException {

        logger.info("Image1 info {}", image1.getOriginalFilename());
        logger.info("Image2 info {}", image2.isEmpty());
        logger.info("Other Info {}", data);

        AppointmentInfoDTO requestDto;
        try {
            requestDto = objectMapper.readValue(data, AppointmentInfoDTO.class);
            if (null == requestDto.getAppointmentId()){
                return ResponseEntity.badRequest().body("Appointment Id is empty");
            }
            if (null == requestDto.getPatientId()){
                return ResponseEntity.badRequest().body("Patient Id is empty");
            }
            if (StringUtils.isNullOrEmpty(requestDto.getPrescription())){
                return ResponseEntity.badRequest().body("Prescription is empty");
            }
            if (StringUtils.isNullOrEmpty(requestDto.getBilling_notes())){
                return ResponseEntity.badRequest().body("Prescription is empty");
            }
            if (requestDto.getAmount() == 0){
                return ResponseEntity.badRequest().body("Amount is empty");
            }

        } catch (JsonProcessingException e) {
            return ResponseEntity.badRequest().body("Bad Request");
        }

        //check appointment is created or not
        Appointments savedAppointment = service.getAppointmentByID(requestDto.getAppointmentId());
        requestDto.setCreatedAt(LocalDateTime.now());
        if (!image1.isEmpty()){
           String fileName = fileService.uploadImage(path+"/"+requestDto.getAppointmentId(), image1);
           requestDto.setImage_1(fileName);
        }
        if (!image2.isEmpty()){
            String fileName = fileService.uploadImage(path+"/"+requestDto.getAppointmentId(), image2);
            requestDto.setImage_2(fileName);
        }
        if (!image3.isEmpty()){
            String fileName = fileService.uploadImage(path+"/"+requestDto.getAppointmentId(), image3);
            requestDto.setImage_3(fileName);
        }
        if (!image4.isEmpty()){
            String fileName = fileService.uploadImage(path+"/"+requestDto.getAppointmentId(), image4);
            requestDto.setImage_4(fileName);
        }
        if (!image5.isEmpty()){
            String fileName = fileService.uploadImage(path+"/"+requestDto.getAppointmentId(), image5);
            requestDto.setImage_5(fileName);
        }

        AppointmentInfoDTO createdDto = service.processAppointment(requestDto, savedAppointment);
        return new ResponseEntity<>(
                new ApiResponse<>(
                        true,
                        "Appointment Info Submitted Successfully",
                        HttpStatus.CREATED.value(),
                        createdDto),
                HttpStatus.CREATED);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ApiResponse<AppointmentInfoDTO>> getAppointmentInfo(@PathVariable("id") UUID appId) {
        AppointmentInfoDTO apiResponse = service.getAppointmentInfo(appId);
        return new ResponseEntity<>(
                new ApiResponse<>(
                        true,
                        "Success",
                        HttpStatus.OK.value(),
                        apiResponse),
                HttpStatus.OK);
    }

    @GetMapping(path = "/image/{appointmentId}/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(@PathVariable("imageName") String imageName,
                              @PathVariable("appointmentId") UUID appointmentId,
                              HttpServletResponse response) throws IOException {

        InputStream inputStream = fileService.getResource(path+"/"+appointmentId, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(inputStream, response.getOutputStream());
    }

}
