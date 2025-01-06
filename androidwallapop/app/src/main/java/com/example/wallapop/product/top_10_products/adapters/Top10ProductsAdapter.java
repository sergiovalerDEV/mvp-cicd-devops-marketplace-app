package com.example.wallapop.product.top_10_products.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wallapop.R;
import com.example.wallapop.beans.Product;
import com.example.wallapop.utils._SV2_INF_THE_BEST;

import java.util.ArrayList;
import java.util.Locale;

public class Top10ProductsAdapter extends RecyclerView.Adapter<Top10ProductsAdapter.ViewHolder> {
    private final ArrayList<Product> topProducts;
    private final Context context;

    public Top10ProductsAdapter(Context context, ArrayList<Product> topProducts) {
        this.context = context;
        this.topProducts = topProducts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_top_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = topProducts.get(position);
        holder.tvProductDescription.setText((position + 1) + ". " + product.getDescripcion());
        holder.tvTotalSales.setText(String.format(Locale.getDefault(), "Ventas: %d", product.getTotal_ventas()));
        holder.tvPrice.setText(String.format(Locale.getDefault(), "Precio: %s", product.getPrecio()));

        String imageUrl = product.getImagen();
        if (imageUrl != null && !imageUrl.isEmpty()) {
            new _SV2_INF_THE_BEST.Builder(imageUrl, holder.ivProductImage)
                    .build()
                    .load();
        }
    }

    @Override
    public int getItemCount() {
        return topProducts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvProductDescription;
        public TextView tvTotalSales;
        public TextView tvPrice;
        public ImageView ivProductImage;

        public ViewHolder(View itemView) {
            super(itemView);
            tvProductDescription = itemView.findViewById(R.id.tvProductDescription);
            tvTotalSales = itemView.findViewById(R.id.tvTotalSales);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            ivProductImage = itemView.findViewById(R.id.ivProductImage);
        }
    }
}