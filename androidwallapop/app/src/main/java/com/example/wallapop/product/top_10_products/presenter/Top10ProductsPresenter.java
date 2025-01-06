package com.example.wallapop.product.top_10_products.presenter;

import com.example.wallapop.beans.Product;
import com.example.wallapop.product.top_10_products.ContractTop10Products;
import com.example.wallapop.product.top_10_products.model.Top10ProductsModel;

import java.util.ArrayList;
import java.util.List;

public class Top10ProductsPresenter implements ContractTop10Products.Presenter, ContractTop10Products.Model.OnLoadTop10ProductsListener {
    private final ContractTop10Products.View view;
    private final Top10ProductsModel model;

    public Top10ProductsPresenter(ContractTop10Products.View view) {
        this.view = view;
        this.model = new Top10ProductsModel();
    }

    @Override
    public void loadTop10Products() {
        model.fetchTop10Products(this);
    }

    @Override
    public void onTop10ProductsLoaded(List<Product> products) {
        // Cambia a List<Product> en lugar de ArrayList<Product>
        if (products != null && !products.isEmpty()) {
            view.showTop10Products(new ArrayList<>(products)); // Convierto a ArrayList para la vista
        } else {
            view.showError("No se encontraron productos.");
        }
    }

    @Override
    public void onFailure(String error) {
        view.showError(error);
    }
}