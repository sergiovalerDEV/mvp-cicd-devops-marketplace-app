package com.example.wallapop.product.top_10_rated_products.presenter;

import com.example.wallapop.beans.Product;
import com.example.wallapop.product.top_10_rated_products.ContractTop10RatedProducts;
import com.example.wallapop.product.top_10_rated_products.model.Top10RatedProductsModel;

import java.util.ArrayList;
import java.util.List;

public class Top10RatedProductsPresenter implements ContractTop10RatedProducts.Presenter, ContractTop10RatedProducts.Model.OnLoadTop10RatedProductsListener {
    private final ContractTop10RatedProducts.View view;
    private final Top10RatedProductsModel model;

    public Top10RatedProductsPresenter(ContractTop10RatedProducts.View view) {
        this.view = view;
        this.model = new Top10RatedProductsModel();
    }

    @Override
    public void loadTop10RatedProducts() {
        model.fetchTop10RatedProducts(this);
    }

    @Override
    public void onTop10RatedProductsLoaded(List<Product> products) {
        if (products != null && !products.isEmpty()) {
            view.showTop10RatedProducts(new ArrayList<>(products));
        } else {
            view.showError("No se encontraron productos valorados.");
        }
    }

    @Override
    public void onFailure(String error) {
        view.showError(error);
    }
}
