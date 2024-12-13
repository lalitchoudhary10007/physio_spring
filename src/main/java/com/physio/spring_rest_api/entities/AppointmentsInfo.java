package com.physio.spring_rest_api.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "appointments_info")
@NoArgsConstructor
@Getter
@Setter
public class AppointmentsInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private UUID patientId;

    @Column(nullable = false)
    private UUID appointmentId;

    @Column(name= "prescription")
    private String prescription;    //eg. Rx

    @Column(name= "billing_notes")
    private String billing_notes;

    @Column(name= "img_1")
    private String image_1;

    @Column(name= "img_2")
    private String image_2;

    @Column(name= "img_3")
    private String image_3;

    @Column(name= "img_4")
    private String image_4;

    @Column(name= "img_5")
    private String image_5;

    @Column(name= "detail_1")
    private String detail_1;

    @Column(name= "detail_2")
    private String detail_2;

    @Column(name= "amount_paid")
    private double amount;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

}
