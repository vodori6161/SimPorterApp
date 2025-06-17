package com.example.SimPorter.repository;

import com.example.SimPorter.model.PortRequest;
import com.example.SimPorter.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
boolean existsByAadhaarAndPhoneNumber(String aadhaar,String phoneNumber);
 Optional<User> findByAadhaarAndPhoneNumber(String aadhaar, String phoneNumber);
}
