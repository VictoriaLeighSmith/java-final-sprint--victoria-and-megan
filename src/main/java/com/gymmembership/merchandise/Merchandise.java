package com.gymmembership.merchandise;

public class Merchandise {

    // Declare variables
    private int merchandiseID;
    private String productName;
    private String type;
    private double price;
    private int stockLevel;

    // Constructors
    public Merchandise(int merchandiseID, String productName, String type, double price, int stockLevel) {
        this.merchandiseID = merchandiseID;
        this.productName = productName;
        this.type = type;
        this.price = price;
        this.stockLevel = stockLevel;
    }

    public Merchandise(String productName, String type, double price, int stockLevel) {
        this.productName = productName;
        this.type = type;
        this.price = price;
        this.stockLevel = stockLevel;
    }

    public Merchandise() {
    }

    // Getter and setter methods
    public int getMerchandiseID() {
        return merchandiseID;
    }

    public void setMerchandiseID(int merchandiseID) {
        this.merchandiseID = merchandiseID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStockLevel() {
        return stockLevel;
    }

    public void setStockLevel(int stockLevel) {
        this.stockLevel = stockLevel;
    }

    // Override toString method
    @Override
    public String toString() {
        return "Merchandise{" +
                "merchandiseID=" + merchandiseID +
                ", productName='" + productName + '\'' +
                ", type='" + type + '\'' +
                ", price=" + price +
                ", stockLevel=" + stockLevel +
                '}';
    }
}
