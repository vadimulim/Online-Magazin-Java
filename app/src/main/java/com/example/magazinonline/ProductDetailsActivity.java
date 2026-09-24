package com.example.magazinonline;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class ProductDetailsActivity extends AppCompatActivity {

    private Product product;
    private int quantity = 1;
    private TextView tvDetailQuantity;
    private ImageButton btnFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        product = (Product) getIntent().getSerializableExtra("product");
        if (product == null) {
            finish();
            return;
        }

        ImageButton btnBackDetails = findViewById(R.id.btnBackDetails);
        btnFavorite = findViewById(R.id.btnFavorite);
        ImageView ivDetailImage = findViewById(R.id.ivDetailImage);
        TextView tvDetailName = findViewById(R.id.tvDetailName);
        TextView tvDetailPrice = findViewById(R.id.tvDetailPrice);
        TextView tvDetailRating = findViewById(R.id.tvDetailRating);
        TextView tvAvailability = findViewById(R.id.tvAvailability);
        TextView tvDetailDescription = findViewById(R.id.tvDetailDescription);
        tvDetailQuantity = findViewById(R.id.tvDetailQuantity);
        ImageButton btnDetailMinus = findViewById(R.id.btnDetailMinus);
        ImageButton btnDetailPlus = findViewById(R.id.btnDetailPlus);
        Button btnDetailAddToCart = findViewById(R.id.btnDetailAddToCart);

        btnBackDetails.setOnClickListener(v -> finish());

        // Populate data
        tvDetailName.setText(product.getName());
        tvDetailPrice.setText(String.format(Locale.getDefault(), "$%.2f", product.getPrice()));
        tvDetailRating.setText(String.format(Locale.getDefault(), "%.1f", product.getRating()));
        tvDetailDescription.setText(product.getDescription());

        if (product.isAvailable()) {
            tvAvailability.setText("In Stock");
            tvAvailability.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
        } else {
            tvAvailability.setText("Out of Stock");
            tvAvailability.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
        }

        ivDetailImage.setImageResource(android.R.drawable.ic_menu_camera);

        updateFavoriteIcon();

        btnFavorite.setOnClickListener(v -> {
            product.setFavorite(!product.isFavorite());
            updateFavoriteIcon();
            String msg = product.isFavorite() ? "Added to Favorites" : "Removed from Favorites";
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        });

        btnDetailPlus.setOnClickListener(v -> {
            quantity++;
            tvDetailQuantity.setText(String.valueOf(quantity));
        });

        btnDetailMinus.setOnClickListener(v -> {
            if (quantity > 1) {
                quantity--;
                tvDetailQuantity.setText(String.valueOf(quantity));
            }
        });

        btnDetailAddToCart.setOnClickListener(v -> {
            for (int i = 0; i < quantity; i++) {
                CartManager.getInstance().addToCart(product);
            }
            Toast.makeText(this, quantity + " x " + product.getName() + " added to cart", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    private void updateFavoriteIcon() {
        if (product.isFavorite()) {
            btnFavorite.setImageResource(android.R.drawable.star_on);
        } else {
            btnFavorite.setImageResource(android.R.drawable.star_off);
        }
    }
}
