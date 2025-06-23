package com.example.SimPorter.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@Table(name = "port_request")
@AllArgsConstructor
@NoArgsConstructor
public class PortRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String aadhaar;
    private PortStatus status;
    private double portingCharge = 100.0;


    private boolean paymentDone;
    private String transactionId;


}
