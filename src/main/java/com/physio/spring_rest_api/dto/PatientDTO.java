package com.physio.spring_rest_api.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PatientDTO {
    private UUID id;

    @NotEmpty(message = "please enter name")
    private String name;

    @NotEmpty
    @Size(min = 10, max = 10, message = "Please enter valid mobile number")
    private String mobile;

    @Email(message = "Please enter valid email address")
    private String email;

    @NotEmpty(message = "please select gender")
    private String gender;

    @Positive(message = "Please enter valid age")
    private int age;

    @NotEmpty(message = "Please enter address")
    private String address;

    private LocalDateTime createdAt;
}
