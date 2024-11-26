package com.physio.spring_rest_api.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AppointmentDTO {

    private UUID id;
    private LocalDateTime createdAt;
    private UUID patientId;
    private String appointmentDate;
    private String appointmentTime;
    private String complaint;
    private String comments;
    private int status;

}
