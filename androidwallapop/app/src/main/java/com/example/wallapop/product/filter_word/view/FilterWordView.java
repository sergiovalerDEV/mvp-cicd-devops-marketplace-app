package com.example.wallapop.product.filter_word.view;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wallapop.R;
import com.example.wallapop.product.filter_word.ContractFilterWord;
import com.example.wallapop.product.filter_word.adapters.FilterWordAdapter;
import com.example.wallapop.product.filter_word.presenter.FilterWordPresenter;
import com.example.wallapop.beans.Product;

import java.util.ArrayList;

public class FilterWordView extends AppCompatActivity implements ContractFilterWord.View {
    private RecyclerView recyclerViewProducts;
    private FilterWordAdapter productsAdapter;
    private FilterWordPresenter presenter;
    private ArrayList<Product> productsList;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_word_view);

        String keywords = getIntent().getStringExtra("KEYWORDS");
        if (keywords == null || keywords.isEmpty()) {
            Toast.makeText(this, "Error: No se proporcionaron palabras clave", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        recyclerViewProducts = findViewById(R.id.recyclerViewProducts);
        progressBar = findViewById(R.id.progressBar);
        recyclerViewProducts.setLayoutManager(new LinearLayoutManager(this));

        productsList = new ArrayList<>();
        productsAdapter = new FilterWordAdapter(this, productsList);
        recyclerViewProducts.setAdapter(productsAdapter);

        presenter = new FilterWordPresenter(this);
        showLoading();
        presenter.fetchProductsByKeywords(keywords);
    }

    @Override
    public void successFilterWord(ArrayList<Product> products) {
        hideLoading();
        if (products != null && !products.isEmpty()) {
            productsList.clear();
            productsList.addAll(products);
            productsAdapter.notifyDataSetChanged();
        } else {
            showError("No se encontraron productos con las palabras clave proporcionadas.");
        }
    }

    @Override
    public void failureFilterWord(String error) {
        hideLoading();
        showError(error);
    }

    private void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerViewProducts.setVisibility(View.GONE);
    }

    private void hideLoading() {
        progressBar.setVisibility(View.GONE);
        recyclerViewProducts.setVisibility(View.VISIBLE);
    }
}