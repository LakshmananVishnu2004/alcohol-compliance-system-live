package com.vishnu.demo.repository;

import com.vishnu.demo.model.Otp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OtpRepository extends JpaRepository<Otp, Long> {

    Optional<Otp> findTopByUserIdOrderByExpiryTimeDesc(Long userId);
}