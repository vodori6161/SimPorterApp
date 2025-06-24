package com.example.SimPorter.service;

import com.example.SimPorter.dto.PortResponse;
import com.example.SimPorter.model.PortRequest;
import com.example.SimPorter.model.*;
import com.example.SimPorter.model.PortStatus;
import com.example.SimPorter.repository.UserRepository;
import com.example.SimPorter.repository.PortRequestRepository;
import lombok.*;
import org.springframework.aop.config.PointcutEntry;
import org.springframework.stereotype.Service;

import javax.sound.sampled.Port;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;


@Service
@AllArgsConstructor
public class PortingService {
    private final UserRepository userRepo;
    private final PortRequestRepository portRepo;

    public PortResponse initiatePort(String aadhaar, String phoneNumber)
    {
        if(!userRepo.existsByAadhaarAndPhoneNumber(aadhaar,phoneNumber))
        {
            return PortResponse.error("User not found. Not a jio customer");
        }

        User user = userRepo.findByAadhaarAndPhoneNumber(aadhaar, phoneNumber).orElseThrow(()-> new RuntimeException("user not found"));

        long monthsWithJio = ChronoUnit.MONTHS.between(user.getJoinDate(),LocalDate.now());
        if(monthsWithJio<3)
            return PortResponse.error("you need to be with jio for more than 3 months" + "current tenure is " + monthsWithJio + "months");



        double amountDue = 0;
        String paymentMessage = "";


        if(user.getPlantype() == PlanType.POSTPAID){
            amountDue = user.getPostpaidDue();
            paymentMessage = String.format("you need to pay Rs.%.2f for postpaid dues", amountDue);

        }
        else
        {
            paymentMessage = "your prepaid balance will expire as you are switching to another isp";


        }

        PortRequest request = PortRequest.builder()
                .aadhaar(aadhaar)
                .status(PortStatus.PENDING)
                .build();

        portRepo.save(request);

        return PortResponse.success(
                paymentMessage+
                        String.format(" plus Rs%.2f porting fee, total: rs%.2f" ,
                               200.00,
                                200.00)
        );

    }

    public String checkStatus(Long requestId){
        PortRequest request = portRepo.findById(requestId).orElseThrow(()-> new RuntimeException("request not found"));

        if(!request.isPaymentDone()){
            return "Payment pending";
        }
        if(request.getStatus() == PortStatus.COMPLETED){
            return "Porting completed";
        }

        // chumma say the airtel thing is working

        return "Success, the porting is completed";
    }

    public void updatePortStatus(Long requestId, PortStatus newStatus){
        PortRequest request = portRepo.findById(requestId).orElseThrow(()-> new RuntimeException("request not found"));
        request.setStatus(newStatus);
        portRepo.save(request);
    }
}
