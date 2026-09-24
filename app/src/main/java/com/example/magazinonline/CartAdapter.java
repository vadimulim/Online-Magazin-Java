package com.example.magazinonline;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Locale;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private final Context context;
    private final List<CartItem> cartItemList;
    private final OnCartActionListener listener;

    public interface OnCartActionListener {
        void onCartChanged();
    }

    public CartAdapter(Context context, List<CartItem> cartItemList, OnCartActionListener listener) {
        this.context = context;
        this.cartItemList = cartItemList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem cartItem = cartItemList.get(position);
        Product product = cartItem.getProduct();

        holder.tvCartItemName.setText(product.getName());
        holder.tvCartItemPrice.setText(String.format(Locale.getDefault(), "$%.2f", product.getPrice()));
        holder.tvQuantity.setText(String.valueOf(cartItem.getQuantity()));

        // Image placeholder
        holder.ivCartItemImage.setImageResource(android.R.drawable.ic_menu_camera);

        holder.btnPlus.setOnClickListener(v -> {
            int newQty = cartItem.getQuantity() + 1;
            CartManager.getInstance().updateQuantity(product.getId(), newQty);
            notifyDataSetChanged();
            if (listener != null) listener.onCartChanged();
        });

        holder.btnMinus.setOnClickListener(v -> {
            int newQty = cartItem.getQuantity() - 1;
            CartManager.getInstance().updateQuantity(product.getId(), newQty);
            notifyDataSetChanged();
            if (listener != null) listener.onCartChanged();
        });

        holder.btnDelete.setOnClickListener(v -> {
            CartManager.getInstance().removeFromCart(product.getId());
            notifyDataSetChanged();
            if (listener != null) listener.onCartChanged();
        });
    }

    @Override
    public int getItemCount() {
        return cartItemList.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView ivCartItemImage;
        TextView tvCartItemName, tvCartItemPrice, tvQuantity;
        ImageButton btnPlus, btnMinus, btnDelete;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            ivCartItemImage = itemView.findViewById(R.id.ivCartItemImage);
            tvCartItemName = itemView.findViewById(R.id.tvCartItemName);
            tvCartItemPrice = itemView.findViewById(R.id.tvCartItemPrice);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            btnPlus = itemView.findViewById(R.id.btnPlus);
            btnMinus = itemView.findViewById(R.id.btnMinus);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
