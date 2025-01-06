package com.example.wallapop.product.filtered_products.presenter;

import com.example.wallapop.product.filtered_products.FilteredProductsContract;
import com.example.wallapop.product.filtered_products.model.FilteredProductsModel;
import com.example.wallapop.beans.Product;

import java.util.List;

public class FilteredProductsPresenter implements FilteredProductsContract.Presenter, FilteredProductsContract.Model.OnFilteredProductsListener {
    private final FilteredProductsContract.View view;
    private final FilteredProductsModel model;

    public FilteredProductsPresenter(FilteredProductsContract.View view) {
        this.view = view;
        this.model = new FilteredProductsModel(this);
    }

    @Override
    public void loadFilteredProducts(int categoryId) {
        view.showLoading();
        model.getFilteredProducts(categoryId);
    }

    @Override
    public void onProductsFetched(List<Product> products) {
        view.hideLoading();
        view.showProducts(products);
    }

    @Override
    public void onFailure(String error) {
        view.hideLoading();
        view.showError(error);
    }
}