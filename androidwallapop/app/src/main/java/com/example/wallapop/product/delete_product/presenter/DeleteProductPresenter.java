package com.example.wallapop.product.delete_product.presenter;

import com.example.wallapop.product.delete_product.ContractDeleteProduct;
import com.example.wallapop.product.delete_product.model.DeleteProductModel;

public class DeleteProductPresenter implements ContractDeleteProduct.Presenter, ContractDeleteProduct.Model.OnDeleteProductListener {
    private ContractDeleteProduct.View view;
    private ContractDeleteProduct.Model model;
    private int lastDeletedProductId;

    public DeleteProductPresenter(ContractDeleteProduct.View view) {
        this.view = view;
        this.model = new DeleteProductModel();
    }

    @Override
    public void deleteProduct(int productId) {
        lastDeletedProductId = productId;
        model.deleteProduct(productId, this);
    }

    @Override
    public void onDeleteSuccess(String message) {
        view.showDeleteSuccess(message);
    }

    @Override
    public void onDeleteFailure(String error) {
        view.showDeleteError(error);
    }

    public int getLastDeletedProductId() {
        return lastDeletedProductId;
    }
}