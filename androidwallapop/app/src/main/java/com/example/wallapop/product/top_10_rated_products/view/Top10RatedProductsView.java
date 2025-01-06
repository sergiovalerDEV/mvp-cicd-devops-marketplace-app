package com.example.wallapop.product.top_10_rated_products.view;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wallapop.R;
import com.example.wallapop.beans.Product;
import com.example.wallapop.product.top_10_rated_products.adapters.Top10RatedProductsAdapter;
import com.example.wallapop.product.top_10_rated_products.presenter.Top10RatedProductsPresenter;
import com.example.wallapop.product.top_10_rated_products.ContractTop10RatedProducts;

import java.util.ArrayList;

public class Top10RatedProductsView extends AppCompatActivity implements ContractTop10RatedProducts.View {
    private RecyclerView recyclerViewTopRatedProducts;
    private Top10RatedProductsAdapter top10RatedProductsAdapter;
    private Top10RatedProductsPresenter presenter;
    private ArrayList<Product> topRatedProductsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top10_rated_products_view);

        recyclerViewTopRatedProducts = findViewById(R.id.recyclerViewTopRatedProducts);
        recyclerViewTopRatedProducts.setLayoutManager(new LinearLayoutManager(this));

        topRatedProductsList = new ArrayList<>();
        top10RatedProductsAdapter = new Top10RatedProductsAdapter(this, topRatedProductsList);
        recyclerViewTopRatedProducts.setAdapter(top10RatedProductsAdapter);

        presenter = new Top10RatedProductsPresenter(this);
        presenter.loadTop10RatedProducts();
    }

    @Override
    public void showTop10RatedProducts(ArrayList<Product> topRatedProducts) {
        if (topRatedProducts != null) {
            topRatedProductsList.clear();
            topRatedProductsList.addAll(topRatedProducts);
            top10RatedProductsAdapter.notifyDataSetChanged();
        } else {
            showError("Error: No se han encontrado productos valorados.");
        }
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}