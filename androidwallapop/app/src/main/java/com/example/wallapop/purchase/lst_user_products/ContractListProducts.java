package com.example.wallapop.purchase.lst_user_products;

import com.example.wallapop.beans.Product;

import java.util.ArrayList;

public interface ContractListProducts {
    interface Presenter {
        void fetchProducts(int userId);
    }

    interface Model {
        interface OnProductsListener {
            void onProductsFetched(ArrayList<Product> products);
            void onFailure(String error);
        }
    }

    interface View {
        void successProducts(ArrayList<Product> products);
        void failureProducts(String error);
    }
}
