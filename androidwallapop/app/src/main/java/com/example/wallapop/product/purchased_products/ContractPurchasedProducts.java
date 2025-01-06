package com.example.wallapop.product.purchased_products;

import com.example.wallapop.beans.Product;
import java.util.ArrayList;

public interface ContractPurchasedProducts {
    interface View {
        void successPurchasedProducts(ArrayList<Product> products);
        void failurePurchasedProducts(String error);
    }

    interface Presenter {
        void fetchPurchasedProducts();
    }

    interface Model {
        interface OnPurchasedProductsListener {
            void onPurchasedProductsFetched(ArrayList<Product> products);
            void onFailure(String error);
        }
        void fetchPurchasedProducts();
    }
}