package com.physio.spring_rest_api.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AppointmentInfoDTO {

    private UUID id;

    private UUID appointmentId;

    private UUID patientId;

    @NotEmpty(message = "Please enter prescription")
    private String prescription;

    @NotEmpty(message = "Please enter billing notes")
    private String billing_notes;

    private String image_1;
    private String image_2;
    private String image_3;
    private String image_4;
    private String image_5;
    private String detail_1;
    private String detail_2;

    @Positive(message = "Please enter valid amount")
    private double amount;

    private LocalDateTime createdAt;

}
