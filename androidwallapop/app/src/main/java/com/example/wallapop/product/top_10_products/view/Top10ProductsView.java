package com.example.wallapop.product.top_10_products.view;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wallapop.R;
import com.example.wallapop.beans.Product;
import com.example.wallapop.product.top_10_products.adapters.Top10ProductsAdapter;
import com.example.wallapop.product.top_10_products.presenter.Top10ProductsPresenter;
import com.example.wallapop.product.top_10_products.ContractTop10Products;

import java.util.ArrayList;

public class Top10ProductsView extends AppCompatActivity implements ContractTop10Products.View {
    private RecyclerView recyclerViewTopProducts;
    private Top10ProductsAdapter top10ProductsAdapter;
    private Top10ProductsPresenter presenter;
    private ArrayList<Product> topProductsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top10_products_view);

        recyclerViewTopProducts = findViewById(R.id.recyclerViewTopProducts);
        recyclerViewTopProducts.setLayoutManager(new LinearLayoutManager(this));

        topProductsList = new ArrayList<>();
        top10ProductsAdapter = new Top10ProductsAdapter(this, topProductsList);
        recyclerViewTopProducts.setAdapter(top10ProductsAdapter);

        presenter = new Top10ProductsPresenter(this);
        presenter.loadTop10Products();
    }

    @Override
    public void showTop10Products(ArrayList<Product> topProducts) {
        if (topProducts != null) {
            topProductsList.clear();
            topProductsList.addAll(topProducts);
            top10ProductsAdapter.notifyDataSetChanged();
        } else {
            showError("Error: No se han encontrado productos.");
        }
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}