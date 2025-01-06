package com.example.wallapop.product.lst_admin_offers;

import com.example.wallapop.beans.Offer;

import java.util.ArrayList;

public interface ContractAdminOffers {
    interface Presenter {
        void fetchOffers();
    }

    interface Model {
        interface OnOffersListener {
            void onOffersFetched(ArrayList<Offer> offers);
            void onFailure(String error);
        }

        void fetchAllOffers();
    }

    interface View {
        void successOffers(ArrayList<Offer> offers);
        void failureOffers(String error);
    }
}