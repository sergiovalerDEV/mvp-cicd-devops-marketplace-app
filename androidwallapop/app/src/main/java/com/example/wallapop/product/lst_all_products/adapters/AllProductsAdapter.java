package com.example.wallapop.product.lst_all_products.adapters;

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
import com.example.wallapop.purchase.buy_product.view.BuyProductView;
import com.example.wallapop.product.category_selection.view.CategorySelectionView;
import com.example.wallapop.product.product_detail.view.ProductDetailView;
import com.example.wallapop.product.rate_product.view.RateProductView;
import com.example.wallapop.utils.UserSession;
import com.example.wallapop.utils._SV2_INF_THE_BEST;

import java.util.ArrayList;

public class AllProductsAdapter extends RecyclerView.Adapter<AllProductsAdapter.ViewHolder> {
    private final ArrayList<Product> products;
    private final Context context;
    private final UserSession userSession;

    public AllProductsAdapter(Context context, ArrayList<Product> products) {
        this.context = context;
        this.products = products;
        this.userSession = UserSession.getInstance();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product_lst_all, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = products.get(position);

        holder.tvDescripcion.setText(product.getDescripcion() != null ? product.getDescripcion() : "Descripción no disponible");
        holder.tvPrecio.setText(String.valueOf(product.getPrecio()));

        // Cargar la imagen
        String imageUrl = product.getImagen();
        if (imageUrl != null && !imageUrl.isEmpty()) {
            Log.d("AllProductsAdapter", "Cargando imagen desde: " + imageUrl);
            new _SV2_INF_THE_BEST.Builder(imageUrl, holder.ivProductImage)
                    .build()
                    .load();
        } else {
            Log.d("AllProductsAdapter", "No hay imagen disponible");
        }

        // Establecer el OnClickListener para el botón de compra
        holder.btnBuyProduct.setOnClickListener(v -> {
            Intent intent = new Intent(context, BuyProductView.class);
            intent.putExtra("PRODUCT_ID", product.getId());
            String description = product.getDescripcion() != null ? product.getDescripcion() : "Sin descripción"; // Manejo de null
            intent.putExtra("PRODUCT_DESCRIPTION", description);
            Log.d("AllProductsAdapter", "Enviando descripción: " + description);
            context.startActivity(intent);
        });

        // Establecer el OnClickListener para el botón de valoración
        holder.itemView.findViewById(R.id.rate_button).setOnClickListener(v -> {
            int productId = product.getId();
            Log.d("AllProductsAdapter", "Product ID para valoración: " + productId);

            Intent intent = new Intent(context, RateProductView.class);
            intent.putExtra("PRODUCT_ID", productId);
            context.startActivity(intent);
        });

        // Establecer el OnClickListener para el botón de filtrar por categoría
        Button btnFilterByCategory = holder.itemView.findViewById(R.id.btnFilterByCategory);
        btnFilterByCategory.setOnClickListener(v -> {
            Intent intent = new Intent(context, CategorySelectionView.class);
            context.startActivity(intent);
        });

        // Nuevo: Establecer el OnClickListener para abrir ProductDetailView al hacer clic en el item
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductDetailView.class);
            intent.putExtra("IMAGE_URL", product.getImagen() != null ? product.getImagen() : "");
            intent.putExtra("DESCRIPCION", product.getDescripcion() != null ? product.getDescripcion() : "Sin descripción");
            intent.putExtra("PRECIO", String.valueOf(product.getPrecio())); // Convert to String
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
        public Button btnBuyProduct;

        public ViewHolder(View itemView) {
            super(itemView);
            tvDescripcion = itemView.findViewById(R.id.tvDescripcion);
            tvPrecio = itemView.findViewById(R.id.tvPrecio);
            ivProductImage = itemView.findViewById(R.id.ivProductImage);
            btnBuyProduct = itemView.findViewById(R.id.btnBuyProduct);
        }
    }
}