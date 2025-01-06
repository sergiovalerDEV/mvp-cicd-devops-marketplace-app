package com.example.wallapop.product.filtered_products;

import com.example.wallapop.beans.Product;
import java.util.List;

public interface FilteredProductsContract {
    interface View {
        void showLoading();
        void hideLoading();
        void showProducts(List<Product> products);
        void showError(String error);
    }

    interface Presenter {
        void loadFilteredProducts(int categoryId);
    }

    interface Model {
        void getFilteredProducts(int categoryId);

        interface OnFilteredProductsListener {
            void onProductsFetched(List<Product> products);
            void onFailure(String error);
        }
    }
}