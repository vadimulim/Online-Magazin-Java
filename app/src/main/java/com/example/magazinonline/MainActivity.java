package com.example.magazinonline;

import android.content.Intent;
import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvCategories, rvProducts;
    private EditText etSearch;
    private ProductAdapter productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvCategories = findViewById(R.id.rvCategories);
        rvProducts = findViewById(R.id.rvProducts);
        etSearch = findViewById(R.id.etSearch);
        ImageView ivProfileIcon = findViewById(R.id.ivProfileIcon);

        setupCategories();
        setupProducts(CartManager.getInstance().getAllProducts());

        ivProfileIcon.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(intent);
        });

        etSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                String query = etSearch.getText().toString().trim();
                Intent intent = new Intent(MainActivity.this, ProductsActivity.class);
                intent.putExtra("search_query", query);
                startActivity(intent);
                return true;
            }
            return false;
        });

        // Bottom Navigation
        LinearLayout navHome = findViewById(R.id.navHome);
        LinearLayout navCart = findViewById(R.id.navCart);
        LinearLayout navProfile = findViewById(R.id.navProfile);

        navCart.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, CartActivity.class));
        });

        navProfile.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, ProfileActivity.class));
        });
    }

    private void setupCategories() {
        List<String> categories = CartManager.getInstance().getCategories();
        CategoryAdapter categoryAdapter = new CategoryAdapter(this, categories, category -> {
            Intent intent = new Intent(MainActivity.this, ProductsActivity.class);
            intent.putExtra("category_name", category);
            startActivity(intent);
        });

        rvCategories.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvCategories.setAdapter(categoryAdapter);
    }

    private void setupProducts(List<Product> products) {
        productAdapter = new ProductAdapter(this, products, product -> {
            Intent intent = new Intent(MainActivity.this, ProductDetailsActivity.class);
            intent.putExtra("product", product);
            startActivity(intent);
        });

        rvProducts.setLayoutManager(new GridLayoutManager(this, 2));
        rvProducts.setAdapter(productAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (productAdapter != null) {
            productAdapter.notifyDataSetChanged();
        }
    }
}
