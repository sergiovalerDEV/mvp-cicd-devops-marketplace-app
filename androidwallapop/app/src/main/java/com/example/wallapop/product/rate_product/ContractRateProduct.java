// rate_product/ContractRateProduct.java
package com.example.wallapop.product.rate_product;

public interface ContractRateProduct {
    interface View {
        void successRate(String message);
        void failureRate(String err);
    }

    interface Presenter {
        void rateProduct(int productId, int userId, int rating);
    }

    interface Model {
        interface OnRateProductListener {
            void onFinished(String message);
            void onFailure(String err);
        }

        void rateProductApi(int productId, int userId, int rating, OnRateProductListener listener);
    }
}
