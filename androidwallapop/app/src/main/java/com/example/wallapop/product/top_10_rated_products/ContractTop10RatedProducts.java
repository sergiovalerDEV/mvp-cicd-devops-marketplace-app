package com.example.wallapop.product.top_10_rated_products;
import com.example.wallapop.beans.Product;

import java.util.ArrayList;
import java.util.List;

public interface ContractTop10RatedProducts {
    interface Presenter {
        void loadTop10RatedProducts();
    }

    interface Model {
        interface OnLoadTop10RatedProductsListener {
            void onTop10RatedProductsLoaded(List<Product> products);
            void onFailure(String error);
        }

        void fetchTop10RatedProducts(OnLoadTop10RatedProductsListener listener);
    }

    interface View {
        void showTop10RatedProducts(ArrayList<Product> topRatedProducts);
        void showError(String error);
    }
}