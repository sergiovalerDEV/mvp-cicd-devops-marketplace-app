package com.example.wallapop.product.upload_product;

import com.example.wallapop.beans.Product;

public interface ContractUploadProduct {
    interface View {
        void successUpload();
        void failureUpload(String err);
    }

    interface Presenter {
        void uploadProduct(Product product);
    }

    interface Model {
        void uploadProduct(Product product, OnUploadProductListener listener);

        interface OnUploadProductListener {
            void onSuccess();
            void onFailure(String err);
        }
    }
}
