package com.example.wallapop.purchase.buy_product;

public interface ContractBuyProduct {
    interface View {
        void successPurchase(String message);
        void failurePurchase(String error);
    }

    interface Presenter {
        void buyProduct(int userId, int productId, String productDescription); // Incluye productDescription
        void confirmPurchase(int purchaseId);
    }

    interface Model {
        interface OnBuyProductListener {
            void onPurchaseSuccess(String message);
            void onPurchaseFailure(String error);
        }

        void purchaseProduct(int userId, int productId, String productDescription); // Incluye productDescription
        void confirmPurchase(int purchaseId);
    }
}
