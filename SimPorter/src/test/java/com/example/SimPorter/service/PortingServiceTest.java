package com.example.SimPorter.service;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.example.SimPorter.model.PortRequest;
import com.example.SimPorter.model.User;
import com.example.SimPorter.repository.UserRepository;
import com.example.SimPorter.model.PlanType;
import com.example.SimPorter.model.PortStatus;
import com.example.SimPorter.repository.PortRequestRepository;
import org.junit.jupiter.api.TestTemplate;
import org.mockito.InjectMocks;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class PortingServiceTest {

    @Mock
    private PortRequestRepository portRepo;

    @Mock
    private UserRepository userRepo;

    @InjectMocks
    private PortingService portingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // intitation of the porting process sucessfully

    @Test
    void initiatePort_WhenPrepaidUser_ReturnsSuccess(){


        // arrange
        User user = new User();
        user.setPlantype(PlanType.PREPAID);
        user.setJoinDate(LocalDate.now().minusMonths(6));

        when(userRepo.existsByAadhaarAndPhoneNumber("1234","9876543210"))
                .thenReturn(true);
        when(userRepo.findByAadhaarAndPhoneNumber("1234","9876543210"))
                .thenReturn(Optional.of(user));
        when(portRepo.save(any(PortRequest.class))).thenAnswer(inv->inv.getArgument(0));


        //act it out

        var response = portingService.initiatePort("1234","9876543210");

        // asssert

        assertTrue(response.isSuccess());
         assertTrue( response.getMessage().contains("your prepaid balance will expire as you are switching to another isp"));

    }
// testing the initiate port with the user not found that is the aadhhar and all that is not right

@Test
void initiatePort_WhenUserNotFound_ReturnsError(){

    // arrange
    when(userRepo.existsByAadhaarAndPhoneNumber("invalid","0000000000")).thenReturn(false);

    // act
    var response = portingService.initiatePort("invalid","0000000000");

    //assert
    assertFalse(response.isSuccess());
    assertEquals("User not found. Not a jio customer",response.getMessage());
}

// checking that case when the customer has been with us for less than 3 months
    @Test
    void initiatePort_WhenTenureLessThan3Months_ReturnsError(){
        // arrange
        User user = new User();
        user.setJoinDate(LocalDate.now().minusMonths(2));

        when(userRepo.existsByAadhaarAndPhoneNumber("1234","0000000000")).thenReturn(true);
        when(userRepo.findByAadhaarAndPhoneNumber("1234","0000000000")).thenReturn(Optional.of(user));

        // act

        var response = portingService.initiatePort("1234","0000000000");

        // assert

        assertFalse(response.isSuccess());
        System.out.println(response.getMessage());
        assertTrue(response.getMessage().contains("need to be with jio for more than 3 months"));

    }

    // now i am testing the port status when the payment is still pending
    @Test
    void checkStatus_WhenPaymentStillPending_ReturnsMessage(){

        // arrange
        PortRequest portRequest = new PortRequest();
        portRequest.setId(1L);
        portRequest.setPaymentDone(false);

        when(portRepo.findById(1L)).thenReturn(Optional.of(portRequest));

        // action

        String status = portingService.checkStatus(1L);

        System.out.println(status);
        assertEquals("Payment pending",status);

    }

    // next we are testing that condition when the requestId is invalid

    @Test
    void checkStatus_WhenRequestNotFound_throwsException()
    {
        // arrange
        when(portRepo.findById(999L)).thenReturn(Optional.empty());

        //assert the mistake
        assertThrows(RuntimeException.class, () -> portingService.checkStatus(999L));
    }


}


