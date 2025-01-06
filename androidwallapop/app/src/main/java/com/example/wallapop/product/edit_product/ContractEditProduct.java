package com.example.wallapop.product.edit_product;

import com.example.wallapop.beans.Product;

public interface ContractEditProduct {
    interface View {
        void showEditSuccess(String message);

        void showEditError(String error);
    }

    interface Presenter {
        void editProduct(Product product);
    }

    interface Model {
        interface OnEditProductListener {
            void onEditSuccess(String message);

            void onEditFailure(String error);
        }

        void editProduct(Product product, OnEditProductListener listener);
    }
}