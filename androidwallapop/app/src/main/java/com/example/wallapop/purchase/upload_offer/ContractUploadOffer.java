package com.example.wallapop.purchase.upload_offer;

import com.example.wallapop.beans.Offer;

public interface ContractUploadOffer {
    interface View {
        void successUpload();
        void failureUpload(String err);
    }

    interface Presenter {
        void uploadOffer(Offer offer);
    }

    interface Model {
        void uploadOffer(Offer offer, OnUploadOfferListener listener);

        interface OnUploadOfferListener {
            void onSuccess();
            void onFailure(String err);
        }
    }
}