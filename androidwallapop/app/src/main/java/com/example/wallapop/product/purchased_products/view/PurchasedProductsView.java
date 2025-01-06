package com.example.wallapop.product.purchased_products.view;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wallapop.R;
import com.example.wallapop.product.purchased_products.ContractPurchasedProducts;
import com.example.wallapop.product.purchased_products.adapters.PurchasedProductsAdapter;
import com.example.wallapop.product.purchased_products.presenter.PurchasedProductsPresenter;
import com.example.wallapop.beans.Product;

import java.util.ArrayList;

public class PurchasedProductsView extends AppCompatActivity implements ContractPurchasedProducts.View {
    private static final String TAG = "PurchasedProductsView";
    private RecyclerView recyclerViewProducts;
    private PurchasedProductsAdapter productsAdapter;
    private PurchasedProductsPresenter presenter;
    private final ArrayList<Product> purchasedProductsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchased_products_view);

        initializeViews();
        setupRecyclerView();
        loadPurchasedProducts();
    }

    private void initializeViews() {
        recyclerViewProducts = findViewById(R.id.recyclerViewProducts);
    }

    private void setupRecyclerView() {
        recyclerViewProducts.setLayoutManager(new LinearLayoutManager(this));
        productsAdapter = new PurchasedProductsAdapter(this, purchasedProductsList);
        recyclerViewProducts.setAdapter(productsAdapter);
    }

    private void loadPurchasedProducts() {
        presenter = new PurchasedProductsPresenter(this);
        presenter.fetchPurchasedProducts();
    }

    @Override
    public void successPurchasedProducts(ArrayList<Product> products) {
        Log.d(TAG, "Productos recibidos: " + products.size());
        runOnUiThread(() -> {
            purchasedProductsList.clear();
            purchasedProductsList.addAll(products);
            productsAdapter.notifyDataSetChanged();

            if (!products.isEmpty()) {
                Log.d(TAG, "Mostrando " + products.size() + " productos");
                Toast.makeText(this, "Se encontraron " + products.size() + " productos", Toast.LENGTH_SHORT).show();
            } else {
                Log.w(TAG, "Lista de productos vacía");
                showError("No se encontraron productos comprados.");
            }
        });
    }

    @Override
    public void failurePurchasedProducts(String error) {
        Log.e(TAG, "Error al cargar productos: " + error);
        runOnUiThread(() -> showError("Error al cargar productos: " + error));
    }

    private void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}