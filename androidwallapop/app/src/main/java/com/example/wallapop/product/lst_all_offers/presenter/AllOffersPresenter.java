package com.example.wallapop.product.lst_all_offers.presenter;

import com.example.wallapop.beans.Offer;
import com.example.wallapop.product.lst_all_offers.ContractAllOffers;
import com.example.wallapop.product.lst_all_offers.model.AllOffersModel;

import java.util.ArrayList;

public class AllOffersPresenter implements ContractAllOffers.Presenter, ContractAllOffers.Model.OnOffersListener {
    private final ContractAllOffers.View view;
    private final AllOffersModel model;

    public AllOffersPresenter(ContractAllOffers.View view) {
        this.view = view;
        this.model = new AllOffersModel(this);
    }

    @Override
    public void fetchOffers() {
        model.fetchAllOffers();
    }

    @Override
    public void onOffersFetched(ArrayList<Offer> offers) {
        view.successOffers(offers);
    }

    @Override
    public void onFailure(String error) {
        view.failureOffers(error);
    }
}