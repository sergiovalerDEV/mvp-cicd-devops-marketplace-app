package com.example.wallapop.product.filter_word;

import com.example.wallapop.beans.Product;

import java.util.ArrayList;

public interface ContractFilterWord {
    interface View {
        void successFilterWord(ArrayList<Product> products);
        void failureFilterWord(String error);
    }

    interface Presenter {
        void fetchProductsByKeywords(String keywords);
    }

    interface Model {
        interface OnFilterWordListener {
            void onFilterWordFetched(ArrayList<Product> products);
            void onFailure(String error);
        }

        void fetchProductsByKeywords(String keywords);
    }
}