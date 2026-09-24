package com.example.magazinonline;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class CheckoutActivity extends AppCompatActivity {

    private EditText etName, etAddress, etCity, etPostalCode, etPhone;
    private TextView tvCheckoutTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        etName = findViewById(R.id.etName);
        etAddress = findViewById(R.id.etAddress);
        etCity = findViewById(R.id.etCity);
        etPostalCode = findViewById(R.id.etPostalCode);
        etPhone = findViewById(R.id.etPhone);
        tvCheckoutTotal = findViewById(R.id.tvCheckoutTotal);
        ImageButton btnBackCheckout = findViewById(R.id.btnBackCheckout);
        Button btnPlaceOrder = findViewById(R.id.btnPlaceOrder);

        btnBackCheckout.setOnClickListener(v -> finish());

        double total = CartManager.getInstance().getTotal();
        tvCheckoutTotal.setText(String.format(Locale.getDefault(), "$%.2f", total));

        btnPlaceOrder.setOnClickListener(v -> validateAndPlaceOrder());
    }

    private void validateAndPlaceOrder() {
        String name = etName.getText().toString().trim();
        String address = etAddress.getText().toString().trim();
        String city = etCity.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();

        if (name.isEmpty() || address.isEmpty() || city.isEmpty() || phone.isEmpty()) {
            Toast.makeText(this, "Please fill in all required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Clear cart
        CartManager.getInstance().clearCart();

        // Show confirmation dialog
        new AlertDialog.Builder(this)
                .setTitle("Order Placed Successfully!")
                .setMessage("Thank you for your purchase, " + name + "!\nYour order is being processed.")
                .setPositiveButton("OK", (dialog, which) -> {
                    Intent intent = new Intent(CheckoutActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                })
                .setCancelable(false)
                .show();
    }
}
