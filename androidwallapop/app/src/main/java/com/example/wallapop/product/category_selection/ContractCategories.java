package com.example.wallapop.product.category_selection;

import com.example.wallapop.beans.Category;

import java.util.ArrayList;

public interface ContractCategories {
    interface View {
        void successCategories(ArrayList<Category> categories);
        void failureCategories(String error);
    }

    interface Presenter {
        void fetchCategories();
    }

    interface Model {
        interface OnCategoriesListener {
            void onCategoriesFetched(ArrayList<Category> categories);
            void onFailure(String error);
        }
        void fetchCategories();
    }
}
