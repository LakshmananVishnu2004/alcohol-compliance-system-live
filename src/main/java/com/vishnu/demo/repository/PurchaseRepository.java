package com.vishnu.demo.repository;

import com.vishnu.demo.model.Purchase;
import com.vishnu.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    // 🔥 UPDATED QUERY
    List<Purchase> findByUserAndPurchaseDate(User user, LocalDate date);
}