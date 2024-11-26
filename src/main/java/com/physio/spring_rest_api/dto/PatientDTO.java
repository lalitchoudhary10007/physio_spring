package com.physio.spring_rest_api.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PatientDTO {
    private UUID id;
    private String name;
    private String mobile;
    private String email;
    private String gender;
    private int age;
    private String address;
    private LocalDateTime createdAt;
}
