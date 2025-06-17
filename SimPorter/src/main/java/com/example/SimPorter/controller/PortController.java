package com.example.SimPorter.controller;
import com.example.SimPorter.dto.PortResponse;
import com.example.SimPorter.service.PortingService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RequestMapping
@RestController
@AllArgsConstructor
public class PortController {
    private final PortingService portingService;

    // we are making endpoints here and we are use
}
