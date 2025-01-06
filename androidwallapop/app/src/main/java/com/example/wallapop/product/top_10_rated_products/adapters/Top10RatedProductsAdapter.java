package com.example.wallapop.product.top_10_rated_products.adapters;

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

public class Top10RatedProductsAdapter extends RecyclerView.Adapter<Top10RatedProductsAdapter.ViewHolder> {
    private final ArrayList<Product> topRatedProducts;
    private final Context context;

    public Top10RatedProductsAdapter(Context context, ArrayList<Product> topRatedProducts) {
        this.context = context;
        this.topRatedProducts = topRatedProducts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_top_rated_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = topRatedProducts.get(position);
        holder.tvProductDescription.setText((position + 1) + ". " + product.getDescripcion());
        holder.tvAverageRating.setText(String.format(Locale.getDefault(), "Valoración media: %.2f", product.getAverage_rating()));
        holder.tvTotalRatings.setText(String.format(Locale.getDefault(), "Total valoraciones: %d", product.getTotal_ratings()));
        holder.tvPrice.setText(String.format(Locale.getDefault(), "Precio: %s€", product.getPrecio()));

        String imageUrl = product.getImagen();
        if (imageUrl != null && !imageUrl.isEmpty()) {
            new _SV2_INF_THE_BEST.Builder(imageUrl, holder.ivProductImage)
                    .build()
                    .load();
        }
    }

    @Override
    public int getItemCount() {
        return topRatedProducts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvProductDescription;
        public TextView tvAverageRating;
        public TextView tvTotalRatings;
        public TextView tvPrice;
        public ImageView ivProductImage;

        public ViewHolder(View itemView) {
            super(itemView);
            tvProductDescription = itemView.findViewById(R.id.tvProductDescription);
            tvAverageRating = itemView.findViewById(R.id.tvAverageRating);
            tvTotalRatings = itemView.findViewById(R.id.tvTotalRatings);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            ivProductImage = itemView.findViewById(R.id.ivProductImage);
        }
    }
}