package com.example.wallapop.purchase.buy_offer.presenter;

import com.example.wallapop.purchase.buy_offer.ContractBuyOffer;
import com.example.wallapop.purchase.buy_offer.model.BuyOfferModel;

public class BuyOfferPresenter implements ContractBuyOffer.Presenter, ContractBuyOffer.Model.OnBuyOfferListener {
    private ContractBuyOffer.View view;
    private ContractBuyOffer.Model model;

    public BuyOfferPresenter(ContractBuyOffer.View view) {
        this.view = view;
        this.model = new BuyOfferModel();
    }

    @Override
    public void buyOffer(int userId, int offerId, String offerDescription) {
        model.purchaseOffer(userId, offerId, offerDescription, this);
    }

    @Override
    public void onPurchaseSuccess(String message) {
        view.successPurchase(message);
    }

    @Override
    public void onPurchaseFailure(String error) {
        view.failurePurchase(error);
    }
}
