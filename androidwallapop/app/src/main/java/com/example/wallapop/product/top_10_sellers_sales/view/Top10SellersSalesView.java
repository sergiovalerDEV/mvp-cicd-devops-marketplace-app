package com.example.wallapop.product.top_10_sellers_sales.view;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wallapop.R;
import com.example.wallapop.beans.User;
import com.example.wallapop.product.top_10_sellers_sales.adapters.Top10SellersSalesAdapter;
import com.example.wallapop.product.top_10_sellers_sales.presenter.Top10SellersSalesPresenter;
import com.example.wallapop.product.top_10_sellers_sales.ContractTop10SellersSales;

import java.util.ArrayList;

public class Top10SellersSalesView extends AppCompatActivity implements ContractTop10SellersSales.View {
    private RecyclerView recyclerViewTopSellers;
    private Top10SellersSalesAdapter top10SellersSalesAdapter;
    private Top10SellersSalesPresenter presenter;
    private ArrayList<User> topSellersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top10_sellers_sales_view);

        recyclerViewTopSellers = findViewById(R.id.recyclerViewTopSellers);
        recyclerViewTopSellers.setLayoutManager(new LinearLayoutManager(this));

        topSellersList = new ArrayList<>();
        top10SellersSalesAdapter = new Top10SellersSalesAdapter(this, topSellersList);
        recyclerViewTopSellers.setAdapter(top10SellersSalesAdapter);

        presenter = new Top10SellersSalesPresenter(this);
        presenter.loadTop10SellersSales();
    }

    @Override
    public void showTop10SellersSales(ArrayList<User> topSellers) {
        if (topSellers != null) {
            topSellersList.clear();
            topSellersList.addAll(topSellers);
            top10SellersSalesAdapter.notifyDataSetChanged();
        } else {
            showError("Error: No se han encontrado vendedores.");
        }
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}