package com.vishnu.demo.controller;

import com.vishnu.demo.dto.ApiResponse;
import com.vishnu.demo.service.PurchaseService;
import org.springframework.web.bind.annotation.*;
import com.vishnu.demo.model.Purchase;

@RestController
@RequestMapping("/api/purchase")
public class PurchaseController {

    private final PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }
    @PostMapping
    public ApiResponse<Purchase> purchaseAlcohol(
            @RequestParam Long userId,
            @RequestParam int quantity) {

        Purchase purchase = purchaseService.makePurchase(userId, quantity);

        return new ApiResponse<>(
                "SUCCESS",
                "Purchase completed successfully",
                purchase
        );
    }
    }
