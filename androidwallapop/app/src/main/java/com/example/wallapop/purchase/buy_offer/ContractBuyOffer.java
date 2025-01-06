package com.example.wallapop.purchase.buy_offer;

public interface ContractBuyOffer {
    interface View {
        void successPurchase(String message);
        void failurePurchase(String error);
    }

    interface Presenter {
        void buyOffer(int userId, int offerId, String offerDescription);
    }

    interface Model {
        interface OnBuyOfferListener {
            void onPurchaseSuccess(String message);
            void onPurchaseFailure(String error);
        }

        void purchaseOffer(int userId, int offerId, String offerDescription, OnBuyOfferListener listener);
    }
}