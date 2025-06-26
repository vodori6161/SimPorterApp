package com.example.SimPorter.repository;

import com.example.SimPorter.model.PortRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

// importing all the required packages and all else, the ones that matters are the mockito and also the junit org

// i have to also have a look at the other orgs

import org.antlr.*;
import org.

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PortRequestRepositoryTest {

    @Mock
    private PortRequestRepository portRequestRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // test: Find port requests by Aadhaar
    @Test
    void findByAadhaar_WhenRequestsExist_ReturnsList() {
        // Arrange
        PortRequest req1 = new PortRequest();
        req1.setId(1L);
        req1.setAadhaar("1234");

        PortRequest req2 = new PortRequest();
        req2.setId(2L);
        req2.setAadhaar("1234");

        when(portRequestRepository.findByAadhaar("1234")).thenReturn(Arrays.asList(req1, req2));

        // Act
        List<PortRequest> result = portRequestRepository.findByAadhaar("1234");

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("1234", result.get(0).getAadhaar());
    }

    // Test: No requests for given Aadhaar
    @Test
    void findByAadhaar_WhenNoRequests_ReturnsEmptyList() {
        // Arrange
        when(portRequestRepository.findByAadhaar("invalid")).thenReturn(List.of());

        // Act
        List<PortRequest> result = portRequestRepository.findByAadhaar("invalid");

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}