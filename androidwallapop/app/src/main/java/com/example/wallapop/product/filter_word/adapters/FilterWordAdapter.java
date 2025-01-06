package com.example.wallapop.product.filter_word.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wallapop.R;
import com.example.wallapop.beans.Product;
import com.example.wallapop.purchase.buy_product.view.BuyProductView;
import com.example.wallapop.product.rate_product.view.RateProductView;
import com.example.wallapop.utils._SV2_INF_THE_BEST;

import java.util.ArrayList;

public class FilterWordAdapter extends RecyclerView.Adapter<FilterWordAdapter.ViewHolder> {
    private final ArrayList<Product> products;
    private final Context context;

    public FilterWordAdapter(Context context, ArrayList<Product> products) {
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product_filter_word, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = products.get(position);
        holder.tvDescripcion.setText(product.getDescripcion());

        // Convertir el precio a double y luego formatear
        try {
            double precio = Double.parseDouble(product.getPrecio());
            holder.tvPrecio.setText(String.format("€%.2f", precio));
        } catch (NumberFormatException e) {
            holder.tvPrecio.setText("Precio no disponible");
        }

        String imageUrl = product.getImagen();
        if (imageUrl != null && !imageUrl.isEmpty()) {
            new _SV2_INF_THE_BEST.Builder(imageUrl, holder.ivProductImage)
                    .build()
                    .load();
        }

        holder.btnBuy.setOnClickListener(v -> {
            Intent intent = new Intent(context, BuyProductView.class);
            intent.putExtra("PRODUCT_ID", product.getId());
            intent.putExtra("PRODUCT_DESCRIPTION", product.getDescripcion());
            context.startActivity(intent);
        });

        holder.btnRate.setOnClickListener(v -> {
            Intent intent = new Intent(context, RateProductView.class);
            intent.putExtra("PRODUCT_ID", product.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvDescripcion;
        public TextView tvPrecio;
        public ImageView ivProductImage;
        public Button btnBuy;
        public Button btnRate;

        public ViewHolder(View itemView) {
            super(itemView);
            tvDescripcion = itemView.findViewById(R.id.tvDescripcion);
            tvPrecio = itemView.findViewById(R.id.tvPrecio);
            ivProductImage = itemView.findViewById(R.id.ivProductImage);
            btnBuy = itemView.findViewById(R.id.btnBuy);
            btnRate = itemView.findViewById(R.id.btnRate);
        }
    }
}
