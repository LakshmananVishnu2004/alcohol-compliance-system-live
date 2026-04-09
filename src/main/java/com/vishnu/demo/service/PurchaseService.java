package com.vishnu.demo.service;
import com.vishnu.demo.exception.InvalidUserException;
import com.vishnu.demo.model.Purchase;

import com.vishnu.demo.exception.PurchaseLimitExceededException;
import com.vishnu.demo.model.User;
import com.vishnu.demo.repository.PurchaseRepository;
import com.vishnu.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@Service
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final UserRepository userRepository;
    private final OtpService otpService;
    private final EmailService emailService;


    public PurchaseService(PurchaseRepository purchaseRepository,
                           UserRepository userRepository,
                           OtpService otpService, EmailService emailService) {
        this.purchaseRepository = purchaseRepository;
        this.userRepository = userRepository;
        this.otpService = otpService;
        this.emailService = emailService;
    }

    // 🔥 AGE-BASED LIMIT LOGIC
    private int getLimitByAge(int age) {
        if (age >= 18 && age <= 25) return 3;
        if (age <= 35) return 5;
        return 7;
    }

    public Purchase makePurchase(Long userId, int quantity) {

        otpService.validateOtpForPurchase(userId);



        // 🔥 FETCH USER
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new InvalidUserException("User not found (Register Pannudaa Venna !"));

        LocalDate today = LocalDate.now();

        // 🔥 GET TODAY PURCHASES
        List<Purchase> todaysPurchases =
                purchaseRepository.findByUserAndPurchaseDate(user, today);

        int totalPurchased = todaysPurchases.stream()
                .mapToInt(Purchase::getQuantity)
                .sum();

        int limit = getLimitByAge(user.getAge());

        // 🔥 VALIDATION
        if (totalPurchased + quantity > limit) {
            throw new PurchaseLimitExceededException(
                    "Limit exceeded! Allowed: " + limit + " per day"
            );
        }

        Purchase purchase = new Purchase(user, quantity, today);

// 🔥 SAVE FIRST
        Purchase saved = purchaseRepository.save(purchase);

// 🔥 SEND EMAIL AFTER SUCCESS
        String emailBody = "Purchase Successful!\nQuantity: " + quantity +
                "\nDate: " + today +
                "\nStay safe.";

        System.out.println("Sending email to: " + user.getEmail());

        emailService.sendPurchaseEmail(user.getEmail(), emailBody);

// 🔥 RETURN SAVED DATA
        return saved;
    }
}