package com.example.wallapop.purchase.lst_user_products.presenter;

import com.example.wallapop.beans.Product;
import com.example.wallapop.purchase.lst_user_products.ContractListProducts;
import com.example.wallapop.purchase.lst_user_products.model.ProductsModel;

import java.util.ArrayList;

public class ProductsPresenter implements ContractListProducts.Presenter, ContractListProducts.Model.OnProductsListener {
    private final ContractListProducts.View view;
    private final ProductsModel model;

    public ProductsPresenter(ContractListProducts.View view) {
        this.view = view;
        this.model = new ProductsModel(this); // Inicializa el modelo con el listener
    }

    @Override
    public void fetchProducts(int userId) {
        model.fetchProductsByUser(userId); // Llama al método del modelo para obtener productos
    }

    @Override
    public void onProductsFetched(ArrayList<Product> products) {
        view.successProducts(products); // Envía los productos a la vista
    }

    @Override
    public void onFailure(String error) {
        view.failureProducts(error); // Envía el error a la vista
    }
}