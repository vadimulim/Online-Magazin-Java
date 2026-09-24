package com.example.magazinonline;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Locale;

public class CartActivity extends AppCompatActivity {

    private RecyclerView rvCartItems;
    private TextView tvEmptyCart, tvSubtotal, tvShipping, tvTotal;
    private LinearLayout checkoutSection;
    private CartAdapter cartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        rvCartItems = findViewById(R.id.rvCartItems);
        tvEmptyCart = findViewById(R.id.tvEmptyCart);
        tvSubtotal = findViewById(R.id.tvSubtotal);
        tvShipping = findViewById(R.id.tvShipping);
        tvTotal = findViewById(R.id.tvTotal);
        checkoutSection = findViewById(R.id.checkoutSection);
        ImageButton btnBackCart = findViewById(R.id.btnBackCart);
        Button btnProceedCheckout = findViewById(R.id.btnProceedCheckout);

        btnBackCart.setOnClickListener(v -> finish());

        setupCartList();
        updateSummary();

        btnProceedCheckout.setOnClickListener(v -> {
            if (CartManager.getInstance().getCartItems().isEmpty()) {
                Toast.makeText(this, "Your cart is empty", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent(CartActivity.this, CheckoutActivity.class);
            startActivity(intent);
        });
    }

    private void setupCartList() {
        cartAdapter = new CartAdapter(this, CartManager.getInstance().getCartItems(), this::updateSummary);
        rvCartItems.setLayoutManager(new LinearLayoutManager(this));
        rvCartItems.setAdapter(cartAdapter);
    }

    private void updateSummary() {
        double subtotal = CartManager.getInstance().getSubtotal();
        double shipping = CartManager.getInstance().getShipping();
        double total = CartManager.getInstance().getTotal();

        tvSubtotal.setText(String.format(Locale.getDefault(), "$%.2f", subtotal));
        tvShipping.setText(String.format(Locale.getDefault(), "$%.2f", shipping));
        tvTotal.setText(String.format(Locale.getDefault(), "$%.2f", total));

        if (CartManager.getInstance().getCartItems().isEmpty()) {
            tvEmptyCart.setVisibility(View.VISIBLE);
            rvCartItems.setVisibility(View.GONE);
            checkoutSection.setVisibility(View.GONE);
        } else {
            tvEmptyCart.setVisibility(View.GONE);
            rvCartItems.setVisibility(View.VISIBLE);
            checkoutSection.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (cartAdapter != null) {
            cartAdapter.notifyDataSetChanged();
            updateSummary();
        }
    }
}
