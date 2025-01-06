package com.example.wallapop.product.lst_admin_offers.presenter;

import com.example.wallapop.beans.Offer;
import com.example.wallapop.product.lst_admin_offers.ContractAdminOffers;
import com.example.wallapop.product.lst_admin_offers.model.AdminOffersModel;

import java.util.ArrayList;

public class AdminOffersPresenter implements ContractAdminOffers.Presenter, ContractAdminOffers.Model.OnOffersListener {
    private final ContractAdminOffers.View view;
    private final AdminOffersModel model;

    public AdminOffersPresenter(ContractAdminOffers.View view) {
        this.view = view;
        this.model = new AdminOffersModel(this);
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