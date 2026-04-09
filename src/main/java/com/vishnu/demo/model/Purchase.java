package com.vishnu.demo.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantity;

    private LocalDate purchaseDate;

    // 🔥 RELATIONSHIP ADDED
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Purchase() {}

    public Purchase(User user, int quantity, LocalDate purchaseDate) {
        this.user = user;
        this.quantity = quantity;
        this.purchaseDate = purchaseDate;
    }

    // GETTERS
    public Long getId() { return id; }
    public int getQuantity() { return quantity; }
    public LocalDate getPurchaseDate() { return purchaseDate; }
    public User getUser() { return user; }

    // SETTERS
    public void setId(Long id) { this.id = id; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setPurchaseDate(LocalDate purchaseDate) { this.purchaseDate = purchaseDate; }
    public void setUser(User user) { this.user = user; }
}