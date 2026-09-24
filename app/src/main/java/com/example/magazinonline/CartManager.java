package com.example.magazinonline;

import java.util.ArrayList;
import java.util.List;

public class CartManager {
    private static CartManager instance;
    private final List<CartItem> cartItems;
    private final List<Product> allProducts;
    private final List<String> categories;

    private CartManager() {
        cartItems = new ArrayList<>();
        allProducts = new ArrayList<>();
        categories = new ArrayList<>();
        initMockData();
    }

    public static synchronized CartManager getInstance() {
        if (instance == null) {
            instance = new CartManager();
        }
        return instance;
    }

    private void initMockData() {
        categories.add("All");
        categories.add("Telefoane");
        categories.add("Laptopuri");
        categories.add("Căști");
        categories.add("Ceasuri");
        categories.add("Accesorii");

        allProducts.add(new Product(1, "iPhone 15", "Telefoane", 799.00, 4.8f, "iphone_15", "Latest Apple iPhone with A16 Bionic chip, advanced dual-camera system, and Dynamic Island.", true));
        allProducts.add(new Product(2, "Samsung Galaxy S24", "Telefoane", 699.00, 4.7f, "galaxy_s24", "AI-powered Samsung flagship with stunning display and powerful Snapdragon processor.", true));
        allProducts.add(new Product(3, "Google Pixel 8", "Telefoane", 599.00, 4.6f, "pixel_8", "Google phone with advanced computational photography and pure Android experience.", true));
        allProducts.add(new Product(4, "MacBook Air", "Laptopuri", 999.00, 4.9f, "macbook_air", "Ultra-thin and powerful laptop featuring Apple M-series chip and all-day battery life.", true));
        allProducts.add(new Product(5, "Lenovo ThinkPad", "Laptopuri", 899.00, 4.5f, "thinkpad", "Reliable business laptop with legendary keyboard, robust security, and great performance.", true));
        allProducts.add(new Product(6, "Sony WH-1000XM5", "Căști", 349.00, 4.8f, "sony_headphones", "Industry-leading noise canceling wireless headphones with exceptional sound quality.", true));
        allProducts.add(new Product(7, "Apple Watch", "Ceasuri", 399.00, 4.7f, "apple_watch", "Advanced health sensors, fitness tracking, and seamless connectivity on your wrist.", true));
        allProducts.add(new Product(8, "Samsung Galaxy Watch", "Ceasuri", 249.00, 4.4f, "galaxy_watch", "Smartwatch with holistic health monitoring, sleek design, and vibrant AMOLED display.", true));
        allProducts.add(new Product(9, "AirPods Pro", "Căști", 249.00, 4.8f, "airpods_pro", "Active Noise Cancellation, Adaptive Audio, and immersive spatial sound experience.", true));
        allProducts.add(new Product(10, "Logitech MX Master", "Accesorii", 99.00, 4.9f, "logitech_mouse", "Advanced wireless precision mouse designed for productivity and ergonomic comfort.", true));
    }

    public List<Product> getAllProducts() {
        return allProducts;
    }

    public List<String> getCategories() {
        return categories;
    }

    public List<Product> getProductsByCategory(String category) {
        if (category == null || category.equals("All")) {
            return allProducts;
        }
        List<Product> filtered = new ArrayList<>();
        for (Product p : allProducts) {
            if (p.getCategory().equalsIgnoreCase(category)) {
                filtered.add(p);
            }
        }
        return filtered;
    }

    public List<Product> searchProducts(String query) {
        if (query == null || query.trim().isEmpty()) {
            return allProducts;
        }
        String lowerQuery = query.toLowerCase().trim();
        List<Product> filtered = new ArrayList<>();
        for (Product p : allProducts) {
            if (p.getName().toLowerCase().contains(lowerQuery) || p.getCategory().toLowerCase().contains(lowerQuery)) {
                filtered.add(p);
            }
        }
        return filtered;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void addToCart(Product product) {
        for (CartItem item : cartItems) {
            if (item.getProduct().getId() == product.getId()) {
                item.setQuantity(item.getQuantity() + 1);
                return;
            }
        }
        cartItems.add(new CartItem(product, 1));
    }

    public void updateQuantity(int productId, int quantity) {
        for (int i = 0; i < cartItems.size(); i++) {
            CartItem item = cartItems.get(i);
            if (item.getProduct().getId() == productId) {
                if (quantity <= 0) {
                    cartItems.remove(i);
                } else {
                    item.setQuantity(quantity);
                }
                break;
            }
        }
    }

    public void removeFromCart(int productId) {
        cartItems.removeIf(item -> item.getProduct().getId() == productId);
    }

    public void clearCart() {
        cartItems.clear();
    }

    public double getSubtotal() {
        double subtotal = 0;
        for (CartItem item : cartItems) {
            subtotal += item.getProduct().getPrice() * item.getQuantity();
        }
        return subtotal;
    }

    public double getShipping() {
        return cartItems.isEmpty() ? 0.0 : 15.0;
    }

    public double getTotal() {
        return cartItems.isEmpty() ? 0.0 : getSubtotal() + getShipping();
    }
}
