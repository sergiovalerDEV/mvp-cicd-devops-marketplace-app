package com.example.wallapop.product.lst_all_products;

import com.example.wallapop.beans.Product;

import java.util.ArrayList;

public interface ContractAllProducts {
    interface Presenter {
        void fetchProducts();
    }

    interface Model {
        interface OnProductsListener {
            void onProductsFetched(ArrayList<Product> products);
            void onFailure(String error);
        }

        void fetchAllProducts();
    }

    interface View {
        void successProducts(ArrayList<Product> products);
        void failureProducts(String error);
    }
}