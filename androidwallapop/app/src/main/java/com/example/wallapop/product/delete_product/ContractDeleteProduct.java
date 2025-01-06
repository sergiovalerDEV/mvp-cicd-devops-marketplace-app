package com.example.wallapop.product.delete_product;

public interface ContractDeleteProduct {
    interface View {
        void showDeleteSuccess(String message);

        void showDeleteError(String error);
    }

    interface Presenter {
        void deleteProduct(int productId);
    }

    interface Model {
        interface OnDeleteProductListener {
            void onDeleteSuccess(String message);

            void onDeleteFailure(String error);
        }

        void deleteProduct(int productId, OnDeleteProductListener listener);
    }
}