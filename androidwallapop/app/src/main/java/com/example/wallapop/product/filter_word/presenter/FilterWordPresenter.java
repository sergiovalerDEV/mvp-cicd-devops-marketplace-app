package com.example.wallapop.product.filter_word.presenter;

import com.example.wallapop.beans.Product;
import com.example.wallapop.product.filter_word.ContractFilterWord;
import com.example.wallapop.product.filter_word.model.FilterWordModel;

import java.util.ArrayList;

public class FilterWordPresenter implements ContractFilterWord.Presenter, ContractFilterWord.Model.OnFilterWordListener {
    private final ContractFilterWord.View view;
    private final FilterWordModel model;

    public FilterWordPresenter(ContractFilterWord.View view) {
        this.view = view;
        this.model = new FilterWordModel(this);
    }

    @Override
    public void fetchProductsByKeywords(String keywords) {
        model.fetchProductsByKeywords(keywords);
    }

    @Override
    public void onFilterWordFetched(ArrayList<Product> products) {
        view.successFilterWord(products);
    }

    @Override
    public void onFailure(String error) {
        view.failureFilterWord(error);
    }
}