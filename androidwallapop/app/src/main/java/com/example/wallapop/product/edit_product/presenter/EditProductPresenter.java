package com.example.wallapop.product.edit_product.presenter;

import com.example.wallapop.beans.Product;
import com.example.wallapop.product.edit_product.ContractEditProduct;
import com.example.wallapop.product.edit_product.model.EditProductModel;

public class EditProductPresenter implements ContractEditProduct.Presenter, ContractEditProduct.Model.OnEditProductListener {
    private ContractEditProduct.View view;
    private ContractEditProduct.Model model;

    public EditProductPresenter(ContractEditProduct.View view) {
        this.view = view;
        this.model = new EditProductModel();
    }

    @Override
    public void editProduct(Product product) {
        model.editProduct(product, this);
    }

    @Override
    public void onEditSuccess(String message) {
        view.showEditSuccess(message);
    }

    @Override
    public void onEditFailure(String error) {
        view.showEditError(error);
    }
}
