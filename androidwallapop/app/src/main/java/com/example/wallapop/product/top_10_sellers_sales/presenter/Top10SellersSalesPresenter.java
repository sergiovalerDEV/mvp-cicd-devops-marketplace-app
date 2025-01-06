package com.example.wallapop.product.top_10_sellers_sales.presenter;

import com.example.wallapop.beans.User;
import com.example.wallapop.product.top_10_sellers_sales.ContractTop10SellersSales;
import com.example.wallapop.product.top_10_sellers_sales.model.Top10SellersSalesModel;

import java.util.ArrayList;
import java.util.List;

public class Top10SellersSalesPresenter implements ContractTop10SellersSales.Presenter, ContractTop10SellersSales.Model.OnLoadTop10SellersSalesListener {
    private final ContractTop10SellersSales.View view;
    private final Top10SellersSalesModel model;

    public Top10SellersSalesPresenter(ContractTop10SellersSales.View view) {
        this.view = view;
        this.model = new Top10SellersSalesModel();
    }

    @Override
    public void loadTop10SellersSales() {
        model.fetchTop10SellersSales(this);
    }

    @Override
    public void onTop10SellersSalesLoaded(List<User> sellers) {
        if (sellers != null && !sellers.isEmpty()) {
            view.showTop10SellersSales(new ArrayList<>(sellers));
        } else {
            view.showError("No se encontraron vendedores.");
        }
    }

    @Override
    public void onFailure(String error) {
        view.showError(error);
    }
}