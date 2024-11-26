package com.physio.spring_rest_api.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "appointments")
@NoArgsConstructor
@Getter
@Setter
public class Appointments {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private UUID patientId;

    @Column(nullable = false)
    private String appointmentDate;

    @Column(nullable = false)
    private String appointmentTime;

    @Column(nullable = false)
    private String complaint;

    private String comments;

    @Column(name="status", columnDefinition="Integer default 0")
    private int status;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;


}
