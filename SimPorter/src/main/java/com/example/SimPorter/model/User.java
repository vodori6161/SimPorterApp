package com.example.SimPorter.model;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "user_table")
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
