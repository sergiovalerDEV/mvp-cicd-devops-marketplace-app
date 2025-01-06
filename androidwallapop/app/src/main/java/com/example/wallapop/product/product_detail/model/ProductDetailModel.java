package com.example.wallapop.product.product_detail.model;

import com.example.wallapop.product.product_detail.ContractProductDetail;

public class ProductDetailModel implements ContractProductDetail.Model {

    @Override
    public void getProductDetails(String imageUrl, String descripcion, double precio, OnProductDetailListener listener) {
        // En este caso, simplemente pasamos los datos recibidos al listener
        listener.onProductDetailLoaded(imageUrl, descripcion, precio);
    }
}