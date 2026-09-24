package com.example.magazinonline;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Locale;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private final Context context;
    private final List<Product> productList;
    private final OnProductClickListener listener;

    public interface OnProductClickListener {
        void onProductClick(Product product);
    }

    public ProductAdapter(Context context, List<Product> productList, OnProductClickListener listener) {
        this.context = context;
        this.productList = productList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.tvProductName.setText(product.getName());
        holder.tvProductPrice.setText(String.format(Locale.getDefault(), "$%.2f", product.getPrice()));
        holder.tvRating.setText(String.format(Locale.getDefault(), "%.1f", product.getRating()));

        // Set mock image resource based on imageUrl tag
        int imageRes = getImageResourceForProduct(product.getImageUrl());
        holder.ivProductImage.setImageResource(imageRes);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onProductClick(product);
            }
        });

        holder.btnAddToCart.setOnClickListener(v -> {
            CartManager.getInstance().addToCart(product);
            Toast.makeText(context, product.getName() + " added to cart", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    private int getImageResourceForProduct(String imageName) {
        if (imageName == null) return R.drawable.ic_search;
        switch (imageName) {
            case "iphone_15":
            case "galaxy_s24":
            case "pixel_8":
                return android.R.drawable.ic_menu_camera; // Using android standard icons as placeholders
            case "macbook_air":
            case "thinkpad":
                return android.R.drawable.ic_menu_manage;
            case "sony_headphones":
            case "airpods_pro":
                return android.R.drawable.ic_media_play;
            case "apple_watch":
            case "galaxy_watch":
                return android.R.drawable.ic_menu_recent_history;
            case "logitech_mouse":
                return android.R.drawable.ic_menu_compass;
            default:
                return R.drawable.ic_search;
        }
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView ivProductImage;
        TextView tvProductName, tvProductPrice, tvRating;
        Button btnAddToCart;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProductImage = itemView.findViewById(R.id.ivProductImage);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvProductPrice = itemView.findViewById(R.id.tvProductPrice);
            tvRating = itemView.findViewById(R.id.tvRating);
            btnAddToCart = itemView.findViewById(R.id.btnAddToCart);
        }
    }
}
