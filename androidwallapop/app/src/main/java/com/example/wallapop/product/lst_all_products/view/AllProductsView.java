package com.example.wallapop.product.lst_all_products.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wallapop.R;
import com.example.wallapop.product.lst_all_products.ContractAllProducts;
import com.example.wallapop.product.lst_all_products.adapters.AllProductsAdapter;
import com.example.wallapop.product.lst_all_products.presenter.AllProductsPresenter;
import com.example.wallapop.beans.Product;
import com.example.wallapop.product.filter_word.view.FilterWordView;

import java.util.ArrayList;

public class AllProductsView extends AppCompatActivity implements ContractAllProducts.View {
    private RecyclerView recyclerViewProducts;
    private AllProductsAdapter productsAdapter;
    private AllProductsPresenter presenter;
    private ArrayList<Product> productsList;
    private EditText etSearchKeywords;
    private Button btnSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_products_view);

        // Inicializa RecyclerView
        recyclerViewProducts = findViewById(R.id.recyclerViewProducts);
        recyclerViewProducts.setLayoutManager(new LinearLayoutManager(this));

        // Inicializa la lista de productos
        productsList = new ArrayList<>();
        productsAdapter = new AllProductsAdapter(this, productsList);
        recyclerViewProducts.setAdapter(productsAdapter);

        // Inicializa el presenter y carga los productos
        presenter = new AllProductsPresenter(this);
        presenter.fetchProducts();

        // Inicializa los elementos de búsqueda
        etSearchKeywords = findViewById(R.id.etSearchKeywords);
        btnSearch = findViewById(R.id.btnSearch);

        btnSearch.setOnClickListener(v -> {
            String keywords = etSearchKeywords.getText().toString().trim();
            if (!keywords.isEmpty()) {
                Intent intent = new Intent(AllProductsView.this, FilterWordView.class);
                intent.putExtra("KEYWORDS", keywords);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Por favor, ingrese palabras clave para buscar", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void successProducts(ArrayList<Product> products) {
        if (products != null) {
            productsList.clear();
            productsList.addAll(products);
            productsAdapter.notifyDataSetChanged();
        } else {
            showError("Error: No se han encontrado productos.");
        }
    }

    @Override
    public void failureProducts(String error) {
        showError(error);
    }

    private void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}