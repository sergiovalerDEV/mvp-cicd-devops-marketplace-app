package com.example.wallapop.product.filtered_products.adapters;

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

public class FilteredProductsAdapter extends RecyclerView.Adapter<FilteredProductsAdapter.ViewHolder> {
    private ArrayList<Product> products;
    private Context context;

    public FilteredProductsAdapter(Context context, ArrayList<Product> products) {
        this.context = context;
        this.products = products;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_filtered_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = products.get(position);
        holder.tvName.setText(product.getDescripcion());
        holder.tvDescription.setText(product.getDescripcion());
        holder.tvPrice.setText(String.format("€%.2f", Double.parseDouble(product.getPrecio())));

        // Cargamos las imágenes con la clase de Alberto
        String imageUrl = product.getImagen();
        if (imageUrl != null && !imageUrl.isEmpty()) {
            new _SV2_INF_THE_BEST.Builder(imageUrl, holder.ivProduct)
                    .build()
                    .load();
        }

        holder.btnBuy.setOnClickListener(v -> {
            Intent intent = new Intent(context, BuyProductView.class);
            intent.putExtra("PRODUCT_ID", product.getId());
            // Add this line to pass the description
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
        TextView tvName, tvDescription, tvPrice;
        ImageView ivProduct;
        Button btnBuy, btnRate;

        public ViewHolder(View view) {
            super(view);
            tvName = view.findViewById(R.id.tvName);
            tvDescription = view.findViewById(R.id.tvDescription);
            tvPrice = view.findViewById(R.id.tvPrice);
            ivProduct = view.findViewById(R.id.ivProduct);
            btnBuy = view.findViewById(R.id.btnBuy);
            btnRate = view.findViewById(R.id.btnRate);
        }
    }
}