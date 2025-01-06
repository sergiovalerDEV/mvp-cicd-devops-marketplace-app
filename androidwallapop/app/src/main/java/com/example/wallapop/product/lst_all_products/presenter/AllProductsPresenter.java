package com.example.wallapop.product.lst_all_products.presenter;

import com.example.wallapop.beans.Product;
import com.example.wallapop.product.lst_all_products.ContractAllProducts;
import com.example.wallapop.product.lst_all_products.model.AllProductsModel;

import java.util.ArrayList;

public class AllProductsPresenter implements ContractAllProducts.Presenter, ContractAllProducts.Model.OnProductsListener {
    private final ContractAllProducts.View view;
    private final AllProductsModel model;

    public AllProductsPresenter(ContractAllProducts.View view) {
        this.view = view;
        this.model = new AllProductsModel(this);
    }

    @Override
    public void fetchProducts() {
        model.fetchAllProducts();
    }

    @Override
    public void onProductsFetched(ArrayList<Product> products) {
        view.successProducts(products);
    }

    @Override
    public void onFailure(String error) {
        view.failureProducts(error);
    }
}