package com.example.wallapop.product.delete_product.view;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.Toast;

import com.example.wallapop.product.delete_product.ContractDeleteProduct;
import com.example.wallapop.product.delete_product.presenter.DeleteProductPresenter;

public class DeleteProductDialog implements ContractDeleteProduct.View {
    //Implementamos un dialog para poder visualizar la ventana de confirmación
    //Así podremos asegurarnos de que realmente queremos borrar un producto
    private Context context;
    private DeleteProductPresenter presenter;
    private OnProductDeletedListener listener;

    public interface OnProductDeletedListener {
        void onProductDeleted(int productId);
    }

    public DeleteProductDialog(Context context, OnProductDeletedListener listener) {
        this.context = context;
        this.presenter = new DeleteProductPresenter(this);
        this.listener = listener;
    }

    public void showDeleteConfirmationDialog(int productId) {
        new AlertDialog.Builder(context)
                .setTitle("Delete Product")
                .setMessage("Are you sure you want to delete this product?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    presenter.deleteProduct(productId);
                })
                .setNegativeButton("No", null)
                .show();
    }

    @Override
    public void showDeleteSuccess(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        if (listener != null) {
            listener.onProductDeleted(presenter.getLastDeletedProductId());
        }
    }

    @Override
    public void showDeleteError(String error) {
        new AlertDialog.Builder(context)
                .setTitle("Error")
                .setMessage("Failed to delete product: " + error)
                .setPositiveButton("OK", null)
                .show();
    }
}