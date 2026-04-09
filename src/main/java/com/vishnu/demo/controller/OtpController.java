package com.vishnu.demo.controller;

import com.vishnu.demo.dto.ApiResponse;
import com.vishnu.demo.service.OtpService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/otp")
public class OtpController {

    private final OtpService otpService;

    public OtpController(OtpService otpService) {
        this.otpService = otpService;
    }

    @PostMapping("/generate")
    public String generateOtp(@RequestParam Long userId) {

        String otp = otpService.generateOtp(userId);

        return "OTP (for testing): " + otp;
    }

    @PostMapping("/verify")
    public ApiResponse<Void> verifyOtp(@RequestParam Long userId,
                            @RequestParam String code) {

        boolean isValid = otpService.verifyOtp(userId, code);

        return new ApiResponse<>(
                "SUCCESS",
                "OTP verified",
                null
        );
    }
}