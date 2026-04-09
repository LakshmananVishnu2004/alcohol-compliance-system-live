package com.vishnu.demo.service;

import com.vishnu.demo.exception.OtpNotFoundException;
import com.vishnu.demo.model.Otp;
import com.vishnu.demo.repository.OtpRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class OtpService {

    private final OtpRepository otpRepository;

    public OtpService(OtpRepository otpRepository) {
        this.otpRepository = otpRepository;
    }

    public String generateOtp(Long userId) {

        String otpCode = String.valueOf(new Random().nextInt(900000) + 100000);

        LocalDateTime expiry = LocalDateTime.now().plusMinutes(5);

        Otp otp = new Otp(userId, otpCode, expiry);

        otpRepository.save(otp);

        return otpCode;
    }

    public boolean verifyOtp(Long userId, String code) {


        Otp otp = otpRepository.findTopByUserIdOrderByExpiryTimeDesc(userId)
                .orElseThrow(() -> new RuntimeException("OTP not found"));

        if (otp.getExpiryTime().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("OTP expired");
        }

        if (!otp.getCode().equals(code)) {
            throw new RuntimeException("Invalid OTP");
        }

        // ✅ mark as verified
        otp.setVerified(true);
        otpRepository.save(otp);

        return true;
    }

    public void validateOtpForPurchase(Long userId) {

        Otp otp = otpRepository.findTopByUserIdOrderByExpiryTimeDesc(userId)
                .orElseThrow(() -> new RuntimeException("OTP not found"));

        if (!otp.isVerified()) {
            throw new RuntimeException("OTP not verified");
        }

        if (otp.getExpiryTime().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("OTP expired");
        }

        // 🔥 OPTIONAL: invalidate after use
        otpRepository.delete(otp);
    }
}