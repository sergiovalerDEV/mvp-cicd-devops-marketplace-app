package com.example.wallapop.product.edit_offer.presenter;

import com.example.wallapop.beans.Offer;
import com.example.wallapop.product.edit_offer.ContractEditOffer;
import com.example.wallapop.product.edit_offer.model.EditOfferModel;

public class EditOfferPresenter implements ContractEditOffer.Presenter,
        ContractEditOffer.Model.OnGetOfferDetailsListener,
        ContractEditOffer.Model.OnEditOfferListener {
    private ContractEditOffer.View view;
    private ContractEditOffer.Model model;

    public EditOfferPresenter(ContractEditOffer.View view) {
        this.view = view;
        this.model = new EditOfferModel();
    }

    @Override
    public void getOfferDetails(int offerId) {
        model.getOfferDetails(offerId, this);
    }

    @Override
    public void editOffer(Offer offer) {
        model.editOffer(offer, this);
    }

    @Override
    public void onGetOfferDetailsSuccess(Offer offer) {
        view.showOfferDetails(offer);
    }

    @Override
    public void onGetOfferDetailsFailure(String error) {
        view.showEditError(error);
    }

    @Override
    public void onEditSuccess(String message) {
        view.showEditSuccess(message);
    }

    @Override
    public void onEditFailure(String error) {
        view.showEditError(error);
    }
}