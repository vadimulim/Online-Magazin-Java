package com.example.magazinonline;

import java.io.Serializable;

public class Product implements Serializable {
    private int id;
    private String name;
    private String category;
    private double price;
    private float rating;
    private String imageUrl;
    private String description;
    private boolean isAvailable;
    private boolean isFavorite;

    public Product(int id, String name, String category, double price, float rating, String imageUrl, String description, boolean isAvailable) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.rating = rating;
        this.imageUrl = imageUrl;
        this.description = description;
        this.isAvailable = isAvailable;
        this.isFavorite = false;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public double getPrice() { return price; }
    public float getRating() { return rating; }
    public String getImageUrl() { return imageUrl; }
    public String getDescription() { return description; }
    public boolean isAvailable() { return isAvailable; }
    public boolean isFavorite() { return isFavorite; }
    public void setFavorite(boolean favorite) { isFavorite = favorite; }
}
