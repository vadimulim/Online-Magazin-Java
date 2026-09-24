package com.example.magazinonline;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ImageButton btnBackProfile = findViewById(R.id.btnBackProfile);
        TextView tvMyOrders = findViewById(R.id.tvMyOrders);
        TextView tvFavorites = findViewById(R.id.tvFavorites);
        TextView tvSettings = findViewById(R.id.tvSettings);
        TextView tvLogout = findViewById(R.id.tvLogout);

        btnBackProfile.setOnClickListener(v -> finish());

        tvMyOrders.setOnClickListener(v -> Toast.makeText(this, "My Orders clicked", Toast.LENGTH_SHORT).show());
        tvFavorites.setOnClickListener(v -> Toast.makeText(this, "Favorites clicked", Toast.LENGTH_SHORT).show());
        tvSettings.setOnClickListener(v -> Toast.makeText(this, "Settings clicked", Toast.LENGTH_SHORT).show());

        tvLogout.setOnClickListener(v -> {
            Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show();
            CartManager.getInstance().clearCart();
            Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }
}
