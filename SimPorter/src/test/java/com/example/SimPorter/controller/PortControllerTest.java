package com.example.SimPorter.controller;

import java.io.*;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import com.example.SimPorter.dto.PortResponse;
import com.example.SimPorter.service.PortingService;
import com.example.SimPorter.service.PaymentService;
import com.example.SimPorter.service.IspIntegrationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;


import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class PortControllerTest {
    @Mock
    private PortingService portingService;
    @Mock
    private PaymentService paymentService;
    @Mock
    private IspIntegrationService ispIntegrationService;
    @InjectMocks
    private PortController portController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    // to make sure the response is not null
    // to make sure that the correct response is obtained for the given input
    // this is me testing the initiate post
    // first the test is to see as to what happens when a valid user is the one we are testing for
    @Test
    void initiatePort_WhenValidUser_ReturnsSuccess()
    {
        when(portingService.initiatePort("1234","9876543210"))
                .thenReturn(PortResponse.success("success"));

        PortResponse response = portController.initiatePort("1234","9876543210");

        assertNotNull(response);
        assertTrue(response.isSuccess());
        assertEquals("success",response.getMessage());

    }


    // next up we are testing the controller and the dto for an invalid user, the output
    // that we are going to get is going to be "invalid"
    //we are going to be using the test annnotation

    @Test
    void initiatePort_WhenUserNotFound_ReturnError(){
        // we are going to follow the AAA
        // arrange act and assert

        // 1a, arrange

        when(portingService.initiatePort("invalid","invalid"))
                .thenReturn(PortResponse.error("User not found"));

        //2a , act
        // the response when the inputs are all invalid
        PortResponse response = portController.initiatePort("invalid","invalid");

        // 3a asserting the response, its result and whether it is the thing we desired

        assertNotNull(response);
        assertFalse(response.isSuccess());
        assertEquals("User not found",response.getMessage());

    }

    //handling the succesful payments when the user pays for the dues left
    @Test
    void handlePayment_WhenValidAmount_ReturnsSuccess(){

        // first a, that is to ARRANGE for teh things to be correct
        ResponseEntity<String> responseEntity = ResponseEntity.ok("success");
        when(paymentService.processPayment(1L,200.0)).thenReturn(responseEntity);

        //now is to ACT on the same
        ResponseEntity<String> response = portController.handlePayment(1L,200.0);

        // asserting that is equals

        assertEquals( responseEntity, response);


    }


    // same thing as above but we are going to get an error as the payment is not successful

    @Test
    void handlePayment_WhenPaymentUnsuccessful_ReturnsError(){
        ResponseEntity<String> responseEntity = ResponseEntity.badRequest().body("Payment unsuccessful");
        when(paymentService.processPayment(1L,200.0)).thenReturn(responseEntity);

        ResponseEntity<String> response = portController.handlePayment(1L,200.0);

        assertEquals(responseEntity,response);

    }


    // i have used this test method to check the port status when the request returns success

    @Test
    void checkPortStatus_WhenRequestSuccessful_ReturnsSuccess(){
        when(portingService.checkStatus(1L)).thenReturn("success");
        String response = portController.checkPortStatus(1L);
        assertEquals("success",response);
    }


    // we had the requestId as 1 and of the long type and the same if not enteres properly then
    // the thing we get will be errorborne

    @Test
    void checkPortStatus_WhenRequestUnsuccessful_ReturnsError(){
        when(portingService.checkStatus(999999L)).thenThrow(new RuntimeException("error"));

        assertThrows(RuntimeException.class, ()->portController.checkPortStatus(999999L));
    }







}
