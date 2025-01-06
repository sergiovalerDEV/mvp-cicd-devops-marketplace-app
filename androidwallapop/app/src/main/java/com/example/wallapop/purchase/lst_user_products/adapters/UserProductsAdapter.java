package com.example.wallapop.purchase.lst_user_products.adapters;

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
import com.example.wallapop.product.delete_product.view.DeleteProductDialog;
import com.example.wallapop.product.edit_product.view.EditProductView;
import com.example.wallapop.utils.UserSession;
import com.example.wallapop.utils._SV2_INF_THE_BEST;

import java.util.ArrayList;

public class UserProductsAdapter extends RecyclerView.Adapter<UserProductsAdapter.ViewHolder> {
    private final ArrayList<Product> products;
    private final Context context;
    private final UserSession userSession;

    public UserProductsAdapter(Context context, ArrayList<Product> products) {
        this.context = context;
        this.products = products;
        this.userSession = UserSession.getInstance();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product_lst_user_products, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = products.get(position);

        holder.tvDescripcion.setText(product.getDescripcion());

        String precioStr = product.getPrecio();
        if (precioStr != null && !precioStr.isEmpty()) {
            if (!precioStr.endsWith("€")) {
                precioStr += " €";
            }
            holder.tvPrecio.setText(precioStr);
        } else {
            holder.tvPrecio.setText("Precio no disponible");
        }

        holder.tvCategoryId.setText("Category: " + product.getCategoryId());
        holder.tvPalabrasClave.setText("Keywords: " + product.getPalabrasClave());

        String imageUrl = product.getImagen();
        if (imageUrl != null && !imageUrl.isEmpty()) {
            new _SV2_INF_THE_BEST.Builder(imageUrl, holder.ivProductImage)
                    .build()
                    .load();
        } else {
            System.out.println("Imagen no encontrada");
        }

        holder.btnEditProduct.setOnClickListener(view -> {
            Intent intent = new Intent(context, EditProductView.class);
            intent.putExtra("id", product.getId());
            intent.putExtra("descripcion", product.getDescripcion());
            intent.putExtra("precio", product.getPrecio());
            intent.putExtra("user_id", product.getUserId());
            intent.putExtra("category_id", product.getCategoryId());
            intent.putExtra("palabras_clave", product.getPalabrasClave());
            intent.putExtra("imagen", product.getImagen());
            context.startActivity(intent);
        });

        holder.btnDeleteProduct.setOnClickListener(view -> {
            DeleteProductDialog deleteDialog = new DeleteProductDialog(context, new DeleteProductDialog.OnProductDeletedListener() {
                @Override
                public void onProductDeleted(int productId) {
                    int deletedPosition = -1;
                    for (int i = 0; i < products.size(); i++) {
                        if (products.get(i).getId() == productId) {
                            deletedPosition = i;
                            break;
                        }
                    }
                    if (deletedPosition != -1) {
                        products.remove(deletedPosition);
                        notifyItemRemoved(deletedPosition);
                        notifyItemRangeChanged(deletedPosition, products.size());
                    }
                }
            });
            deleteDialog.showDeleteConfirmationDialog(product.getId());
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvDescripcion;
        public TextView tvPrecio;
        public TextView tvCategoryId;
        public TextView tvPalabrasClave;
        public ImageView ivProductImage;
        public Button btnEditProduct;
        public Button btnDeleteProduct;

        public ViewHolder(View itemView) {
            super(itemView);
            tvDescripcion = itemView.findViewById(R.id.tvDescripcion);
            tvPrecio = itemView.findViewById(R.id.tvPrecio);
            tvCategoryId = itemView.findViewById(R.id.tvCategoryId);
            tvPalabrasClave = itemView.findViewById(R.id.tvPalabrasClave);
            ivProductImage = itemView.findViewById(R.id.ivProductImage);
            btnEditProduct = itemView.findViewById(R.id.btnEditProduct);
            btnDeleteProduct = itemView.findViewById(R.id.btnDeleteProduct);
        }
    }
}