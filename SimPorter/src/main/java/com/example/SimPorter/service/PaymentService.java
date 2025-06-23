package com.example.SimPorter.service;

import com.example.SimPorter.model.PortRequest;
import com.example.SimPorter.model.PlanType;
import com.example.SimPorter.model.User;
import com.example.SimPorter.repository.PortRequestRepository;
import com.example.SimPorter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentService {

    @Autowired
    private PortRequestRepository portRepo;

    @Autowired
    private UserRepository userRepo;

    /**
     * Processes payment for a porting request
     * @param requestId ID of the port request
     * @param amountPaid Amount provided by the user
     * @return ResponseEntity with success/error message
     */
    public ResponseEntity<String> processPayment(Long requestId, double amountPaid) {
        // Validate request exists
        Optional<PortRequest> requestOpt = portRepo.findById(requestId);
        if (requestOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Request not found");
        }

        PortRequest request = requestOpt.get();

        // Validate user exists using aadhaar from the request
        Optional<User> userOpt = userRepo.findById(request.getAadhaar());
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("User not found");
        }

        User user = userOpt.get();

        // Calculate total due
        double portingFee = request.getPortingCharge();
        double planDue = (user.getPlantype() == PlanType.POSTPAID) ? user.getPostpaidDue() : 0;
        double totalDue = portingFee + planDue;

        // Validate payment amount
        if (amountPaid < totalDue) {
            return ResponseEntity.badRequest()
                    .body(String.format("Insufficient payment. Required: ₹%.2f", totalDue));
        }

        // Mark payment as done and update transaction details
        request.setPaymentDone(true);
        request.setTransactionId("TXN_" + System.currentTimeMillis());
        portRepo.save(request);

        return ResponseEntity.ok(String.format(
                "Payment successful! Transaction ID: %s | Total paid: ₹%.2f",
                request.getTransactionId(), amountPaid
        ));
    }
}

