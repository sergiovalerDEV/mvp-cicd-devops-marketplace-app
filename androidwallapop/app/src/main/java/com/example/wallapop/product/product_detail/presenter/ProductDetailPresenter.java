package com.example.wallapop.product.product_detail.presenter;

import com.example.wallapop.product.product_detail.ContractProductDetail;
import com.example.wallapop.product.product_detail.model.ProductDetailModel;

public class ProductDetailPresenter implements ContractProductDetail.Presenter, ContractProductDetail.Model.OnProductDetailListener {

    private ContractProductDetail.View view;
    private ContractProductDetail.Model model;

    public ProductDetailPresenter(ContractProductDetail.View view) {
        this.view = view;
        this.model = new ProductDetailModel();
    }

    @Override
    public void loadProductDetails(String imageUrl, String descripcion, double precio) {
        model.getProductDetails(imageUrl, descripcion, precio, this);
    }

    @Override
    public void onProductDetailLoaded(String imageUrl, String descripcion, double precio) {
        view.showProductDetails(imageUrl, descripcion, precio);
    }
}
