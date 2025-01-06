package com.example.wallapop.product.product_detail;

public interface ContractProductDetail {
    interface View {
        void showProductDetails(String imageUrl, String descripcion, double precio);
    }

    interface Presenter {
        void loadProductDetails(String imageUrl, String descripcion, double precio);
    }

    interface Model {
        void getProductDetails(String imageUrl, String descripcion, double precio, OnProductDetailListener listener);

        interface OnProductDetailListener {
            void onProductDetailLoaded(String imageUrl, String descripcion, double precio);
        }
    }
}