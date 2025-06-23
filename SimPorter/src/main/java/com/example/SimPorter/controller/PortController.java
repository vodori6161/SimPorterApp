package com.example.SimPorter.controller;
import com.example.SimPorter.dto.PortResponse;
import com.example.SimPorter.service.IspIntegrationService;
import com.example.SimPorter.service.PaymentService;
import com.example.SimPorter.service.PortingService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/port")
@AllArgsConstructor
public class PortController {
    private final PortingService portingService;
    private final PaymentService paymentService;
    private final IspIntegrationService ispIntegrationService;



    // we are making endpoints here
    // the first is the authentication
    @PostMapping("/initiate")
    public PortResponse initiatePort(
            @RequestParam String aadhaar,
            @RequestParam String phoneNumber){
    return portingService.initiatePort(aadhaar, phoneNumber);
}

    @GetMapping("/payment")
    public ResponseEntity<String> handlePayment(
            @RequestParam Long requestId,
            @RequestParam double amount){
        return paymentService.processPayment(requestId, amount);
    }


    @GetMapping("/status")
    public String checkPortStatus(@RequestParam Long requestId){
        return portingService.checkStatus(requestId);
    }





}
