package com.example.wallapop.product.category_selection.presenter;

import com.example.wallapop.beans.Category;
import com.example.wallapop.product.category_selection.ContractCategories;
import com.example.wallapop.product.category_selection.model.CategoriesModel;

import java.util.ArrayList;

public class CategoriesPresenter implements ContractCategories.Presenter, ContractCategories.Model.OnCategoriesListener {
    private final ContractCategories.View view;
    private final CategoriesModel model;

    public CategoriesPresenter(ContractCategories.View view) {
        this.view = view;
        this.model = new CategoriesModel(this);
    }

    @Override
    public void fetchCategories() {
        model.fetchCategories();
    }

    @Override
    public void onCategoriesFetched(ArrayList<Category> categories) {
        view.successCategories(categories);
    }

    @Override
    public void onFailure(String error) {
        view.failureCategories(error);
    }
}