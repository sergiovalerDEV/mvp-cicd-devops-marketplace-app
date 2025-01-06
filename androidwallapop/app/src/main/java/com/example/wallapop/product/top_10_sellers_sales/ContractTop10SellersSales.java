package com.example.wallapop.product.top_10_sellers_sales;

import com.example.wallapop.beans.User;

import java.util.ArrayList;
import java.util.List;

public interface ContractTop10SellersSales {
    interface View {
        void showTop10SellersSales(ArrayList<User> topSellers);
        void showError(String message);
    }

    interface Presenter {
        void loadTop10SellersSales();
    }

    interface Model {
        interface OnLoadTop10SellersSalesListener {
            void onTop10SellersSalesLoaded(List<User> sellers);
            void onFailure(String error);
        }

        void fetchTop10SellersSales(OnLoadTop10SellersSalesListener listener);
    }
}