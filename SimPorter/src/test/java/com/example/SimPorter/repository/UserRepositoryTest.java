package com.example.SimPorter.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import com.example.SimPorter.model.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.Mock;
import java.util.Optional;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserRepositoryTest {
    @Mock
    private UserRepository userRepository;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // what we are doing here is to test the one that finds the by aadhaar
    @Test
    void findByAadhaarAndPhoneNumber_whenUserExists_ReturnsUser(){
        // arrange
        User user = new User();
        user.setAadhaar("1234");
        user.setPhoneNumber("9876543210");

        when(userRepository.findByAadhaarAndPhoneNumber("1234","9876543210"))
                .thenReturn(Optional.of(user));


        // act

        Optional<User> result = userRepository.findByAadhaarAndPhoneNumber("1234","9876543210");

        // assert

        assertTrue(result.isPresent());
        assertEquals(user,result.get());
        assertEquals(user.getPhoneNumber(),result.get().getPhoneNumber());
    }

    // what if the user doesnt exist, this is what we will be testing here

    @Test
    void findByAadhaarAndPhoneNumber_whenUserDoesNotExist_ReturnsUser(){
        //arrange

        //supposed to have returned a user if there was a user but there aint any
        when(userRepository.findByAadhaarAndPhoneNumber("invalid","00000000")).thenReturn(Optional.empty());

        // act
        Optional<User> result = userRepository.findByAadhaarAndPhoneNumber("invalid","00000000");

        assertFalse(result.isPresent());

    }

    // also maybe just check if the user exists

    @Test
    void existsByAadhaarAndPhoneNumber_WhenUserExists_ReturnsTrue(){
        // arrange
        when(userRepository.existsByAadhaarAndPhoneNumber("1234","986543210")).thenReturn(true);


        // act
        boolean exists= userRepository.existsByAadhaarAndPhoneNumber("1234","986543210");


        //assert

        assertTrue(exists);


    }

    // same thing but the user doesnt exist

    @Test
    void existsByAadhaarAndPhoneNumber_WhenUserNotFound_ReturnFalse()
    {
        //actions
        when(userRepository.existsByAadhaarAndPhoneNumber("virat","99944324234"))
                .thenReturn(false);
        // assert
        boolean checkMaadu = userRepository.existsByAadhaarAndPhoneNumber("virat", "99944324234");

        assertFalse(checkMaadu);


    }
}
