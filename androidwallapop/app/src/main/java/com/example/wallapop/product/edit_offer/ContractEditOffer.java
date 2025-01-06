package com.example.wallapop.product.edit_offer;

import com.example.wallapop.beans.Offer;

public interface ContractEditOffer {
    interface View {
        void showOfferDetails(Offer offer);
        void showEditSuccess(String message);
        void showEditError(String error);
    }

    interface Presenter {
        void getOfferDetails(int offerId);
        void editOffer(Offer offer);
    }

    interface Model {
        interface OnGetOfferDetailsListener {
            void onGetOfferDetailsSuccess(Offer offer);
            void onGetOfferDetailsFailure(String error);
        }

        interface OnEditOfferListener {
            void onEditSuccess(String message);
            void onEditFailure(String error);
        }

        void getOfferDetails(int offerId, OnGetOfferDetailsListener listener);
        void editOffer(Offer offer, OnEditOfferListener listener);
    }
}