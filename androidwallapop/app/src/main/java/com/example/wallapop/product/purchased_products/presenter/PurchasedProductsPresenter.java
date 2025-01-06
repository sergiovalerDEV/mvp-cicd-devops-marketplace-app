package com.example.wallapop.product.purchased_products.presenter;

import com.example.wallapop.beans.Product;
import com.example.wallapop.product.purchased_products.ContractPurchasedProducts;
import com.example.wallapop.product.purchased_products.model.PurchasedProductsModel;

import java.util.ArrayList;

public class PurchasedProductsPresenter implements ContractPurchasedProducts.Presenter,
        ContractPurchasedProducts.Model.OnPurchasedProductsListener {

    private final ContractPurchasedProducts.View view;
    private final PurchasedProductsModel model;

    public PurchasedProductsPresenter(ContractPurchasedProducts.View view) {
        this.view = view;
        this.model = new PurchasedProductsModel(this);
    }

    @Override
    public void fetchPurchasedProducts() {
        model.fetchPurchasedProducts();
    }

    @Override
    public void onPurchasedProductsFetched(ArrayList<Product> products) {
        view.successPurchasedProducts(products);
    }

    @Override
    public void onFailure(String error) {
        view.failurePurchasedProducts(error);
    }
}