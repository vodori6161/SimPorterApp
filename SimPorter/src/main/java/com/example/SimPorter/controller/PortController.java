package com.example.SimPorter.controller;
import com.example.SimPorter.dto.PortResponse;
import com.example.SimPorter.service.PortingService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/port")
@AllArgsConstructor
public class PortController {
    private final PortingService portingService;

    // we are making endpoints here and we are use
    @PostMapping("/initiate")
    public PortResponse initiatePort(
            @RequestParam String aadhaar,
            @RequestParam String phoneNumber){

    return portingService.initiatePort(aadhaar, phoneNumber);

}
}
