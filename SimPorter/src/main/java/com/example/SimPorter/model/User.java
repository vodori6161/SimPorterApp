package com.example.SimPorter.model;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    private String aadhaar;
    private String phoneNumber;
    private LocalDate joinDate;
    @Enumerated(EnumType.STRING)
    private PlanType plantype;
    private double balance;
    private double postpaidDue;

}
