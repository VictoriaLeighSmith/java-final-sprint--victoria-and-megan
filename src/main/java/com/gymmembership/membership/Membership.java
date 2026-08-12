package com.gymmembership.membership;

import java.time.LocalDate;

// Represents a gym membership purchased by a user
public class Membership {

    private int membershipId;
    private int userId;
    private String membershipType;
    private double price;
    private LocalDate purchaseDate;

    // No-argument constructor
    public Membership() {

    }

    // Creates a Membership object using all membership details
    public Membership(int membershipId, int userId, String membershipType,
                      double price, LocalDate purchaseDate) {
        this.membershipId = membershipId;
        this.userId = userId;
        this.membershipType = membershipType;
        this.price = price;
        this.purchaseDate = purchaseDate;
    }

    public int getMembershipId() {
        return membershipId;
    }

    public void setMembershipId(int membershipId) {
        this.membershipId = membershipId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getMembershipType() {
        return membershipType;
    }

    public void setMembershipType(String membershipType) {
        this.membershipType = membershipType;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    // Returns a readable representation of the membership
    @Override
    public String toString() {
        return "Membership{" +
                "membershipId=" + membershipId +
                ", userId=" + userId +
                ", membershipType='" + membershipType + '\'' +
                ", price=" + price +
                ", purchaseDate=" + purchaseDate +
                '}';
    }
}