package com.example.magazinonline;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProductsActivity extends AppCompatActivity {

    private RecyclerView rvProductsList;
    private TextView tvCategoryTitle, tvEmpty;
    private ProductAdapter productAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        rvProductsList = findViewById(R.id.rvProductsList);
        tvCategoryTitle = findViewById(R.id.tvCategoryTitle);
        tvEmpty = findViewById(R.id.tvEmpty);
        ImageButton btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(v -> finish());

        String category = getIntent().getStringExtra("category_name");
        String searchQuery = getIntent().getStringExtra("search_query");

        List<Product> productList;
        if (searchQuery != null) {
            tvCategoryTitle.setText("Search: " + searchQuery);
            productList = CartManager.getInstance().searchProducts(searchQuery);
        } else if (category != null) {
            tvCategoryTitle.setText(category);
            productList = CartManager.getInstance().getProductsByCategory(category);
        } else {
            tvCategoryTitle.setText("All Products");
            productList = CartManager.getInstance().getAllProducts();
        }

        if (productList.isEmpty()) {
            tvEmpty.setVisibility(View.VISIBLE);
            rvProductsList.setVisibility(View.GONE);
        } else {
            tvEmpty.setVisibility(View.GONE);
            rvProductsList.setVisibility(View.VISIBLE);
            productAdapter = new ProductAdapter(this, productList, product -> {
                Intent intent = new Intent(ProductsActivity.this, ProductDetailsActivity.class);
                intent.putExtra("product", product);
                startActivity(intent);
            });
            rvProductsList.setLayoutManager(new GridLayoutManager(this, 2));
            rvProductsList.setAdapter(productAdapter);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (productAdapter != null) {
            productAdapter.notifyDataSetChanged();
        }
    }
}
