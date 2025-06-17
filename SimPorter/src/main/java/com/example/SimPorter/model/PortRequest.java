package com.example.SimPorter.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class PortRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String aadhaar;
    private PortStatus status;
    private double postingCharge = 100.0;
}
