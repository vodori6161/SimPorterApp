package com.example.SimPorter.model;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Data
public class User {
    @Id
    private String aadhaar;
    private String phoneNumber;
    private LocalDate joinDate;
    private PlanType plantype;
    private double balance;
    private double postpaidDue;
}
