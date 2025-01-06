package com.example.wallapop.product.filtered_products.view;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.wallapop.R;
import com.example.wallapop.product.filtered_products.FilteredProductsContract;
import com.example.wallapop.product.filtered_products.adapters.FilteredProductsAdapter;
import com.example.wallapop.product.filtered_products.presenter.FilteredProductsPresenter;
import com.example.wallapop.beans.Product;

import java.util.ArrayList;
import java.util.List;

public class FilteredProductsView extends AppCompatActivity implements FilteredProductsContract.View {
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private FilteredProductsAdapter adapter;
    private FilteredProductsPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtered_products_view);

        setupViews();
        setupPresenter();
        loadProducts();
    }

    private void setupViews() {
        recyclerView = findViewById(R.id.recyclerViewProducts);
        progressBar = findViewById(R.id.progressBar);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setupPresenter() {
        presenter = new FilteredProductsPresenter(this);
    }

    private void loadProducts() {
        int categoryId = getIntent().getIntExtra("CATEGORY_ID", -1);
        if (categoryId != -1) {
            presenter.loadFilteredProducts(categoryId);
        }
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showProducts(List<Product> products) {
        adapter = new FilteredProductsAdapter(this, new ArrayList<>(products));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }
}