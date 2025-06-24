package com.example.SimPorter.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.ignoreStubs;
import static org.mockito.Mockito.when;

import com.example.SimPorter.model.PortRequest;
import com.example.SimPorter.model.User;
import com.example.SimPorter.model.PlanType;
import com.example.SimPorter.repository.PortRequestRepository;
import com.example.SimPorter.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import java.util.Optional;

public class PaymentServiceTest {

    // here i will be creating and injecting the mock objects into these and the annoatations
    // to simulate the use of an external dependency like that of services or repositories
    @Mock
    private PortRequestRepository portRepo;
    @Mock
    private UserRepository userRepo;


    // now we have the mock dependencies and the same will be injected into the same
    // here the class we have is the paymentService and the same has all these mock
    // empty empty objects
    @InjectMocks
    private PaymentService paymentService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // testing and processing the payment with a valid user with his valid request

    @Test
    void processPayment_WhenValidRequestAndValidUser_ReturnsSuccess(){
        // Arrange
        PortRequest request = new PortRequest();// object of the class
        request.setId(1L); // this is the legit Id and the same as we know is 1
        request.setPortingCharge(100.00);
        request.setPaymentDone(false);


        User user = new User();
        user.setPlantype(PlanType.POSTPAID);
        user.setPostpaidDue(50.0);

        when(portRepo.findById(1L))
                .thenReturn(Optional.of(request));

        when(userRepo.findByAadhaar("1234"))
                .thenReturn(Optional.of(user));

        // acting on the same

        ResponseEntity<String> response = paymentService.processPayment(1L,150.0);


        //asserting

        assertNotNull(response);
        assertNotNull(response.getBody());
        System.out.println(response.getBody());
        assertTrue(response.getBody().contains("User not found"));
        assertTrue(request.isPaymentDone());

    }


    @Test
    void processPayment_WhenInvalidRequestId_ReturnsError(){
        //arranging
        when(portRepo.findById(99L)).thenReturn(Optional.empty());

        //actt
        ResponseEntity<String> resonse = paymentService.processPayment(99L,100.0);

        //Assert
        assertNotNull(resonse);
        assertEquals("Request not found",resonse.getBody());
    }


    @Test
    void processPayment_WhenAmountTooLow_ReturnsError(){

        // Arrange
        PortRequest request = new PortRequest();
        request.setId(1L);
        request.setPortingCharge(100.00);

        when(portRepo.findById(1L)).thenReturn(Optional.of(request));
        when(userRepo.findByAadhaar("1234")).thenReturn(Optional.of(new User()));

        // Act

        ResponseEntity<String> response = paymentService.processPayment(1L,50.0);

        //assertion
        assertNotNull(response);
        assertTrue(response.getBody().contains("Insufficient payment"));

    }


}
