package com.example.SimPorter.repository;

import com.example.SimPorter.model.PortRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PortRequestRepository extends JpaRepository<PortRequest,Long> {
    List<PortRequest> findByAadhaar(String aadhaar);

}
