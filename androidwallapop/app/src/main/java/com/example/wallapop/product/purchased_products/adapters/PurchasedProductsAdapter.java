package com.example.wallapop.product.purchased_products.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
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
import com.example.wallapop.product.rate_product.view.RateProductView;
import com.example.wallapop.utils._SV2_INF_THE_BEST;

import java.util.ArrayList;

public class PurchasedProductsAdapter extends RecyclerView.Adapter<PurchasedProductsAdapter.ViewHolder> {
    private static final String TAG = "PurchasedProductsAdapter";
    private final Context context;
    private final ArrayList<Product> products;

    public PurchasedProductsAdapter(Context context, ArrayList<Product> products) {
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_purchased_products, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = products.get(position);

        holder.tvDescripcion.setText(product.getDescripcion() != null ? product.getDescripcion() : "Descripción no disponible");
        holder.tvPrecio.setText(String.valueOf(product.getPrecio()));

        String imageUrl = product.getImagen();
        if (imageUrl != null && !imageUrl.isEmpty()) {
            Log.d(TAG, "Cargando imagen desde: " + imageUrl);
            new _SV2_INF_THE_BEST.Builder(imageUrl, holder.ivProductImage)
                    .build()
                    .load();
        } else {
            Log.d(TAG, "No hay imagen disponible");
        }

        // Configuración del botón de valorar
        holder.btnRateProduct.setVisibility(View.VISIBLE);
        holder.btnRateProduct.setOnClickListener(v -> {
            Intent intent = new Intent(context, RateProductView.class);
            intent.putExtra("PRODUCT_ID", product.getId());
            String description = product.getDescripcion() != null ? product.getDescripcion() : "Sin descripción"; // Manejo de null
            intent.putExtra("PRODUCT_DESCRIPTION", description);
            Log.d("PurchasedProductsAdapter", "Enviando descripción: " + description);
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
        public Button btnRateProduct;

        public ViewHolder(View itemView) {
            super(itemView);
            tvDescripcion = itemView.findViewById(R.id.tvDescripcion);
            tvPrecio = itemView.findViewById(R.id.tvPrecio);
            ivProductImage = itemView.findViewById(R.id.ivProductImage);
            btnRateProduct = itemView.findViewById(R.id.rate_button);
        }
    }
}