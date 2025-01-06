package com.example.wallapop.purchase.upload_offer.presenter;

import android.content.Context;
import android.content.Intent;
import com.example.wallapop.beans.Offer;
import com.example.wallapop.product.lst_all_offers.view.AllOffersView;
import com.example.wallapop.purchase.upload_offer.ContractUploadOffer;
import com.example.wallapop.purchase.upload_offer.model.UploadOfferModel;
import com.example.wallapop.utils.UserSession;

public class UploadOfferPresenter implements ContractUploadOffer.Presenter, ContractUploadOffer.Model.OnUploadOfferListener {
    private final ContractUploadOffer.View view;
    private final ContractUploadOffer.Model model;
    private final Context context;

    public UploadOfferPresenter(Context context, ContractUploadOffer.View view) {
        this.context = context;
        this.view = view;
        this.model = new UploadOfferModel();
    }

    @Override
    public void uploadOffer(Offer offer) {
        Integer currentUserRoleId = UserSession.getInstance().getRole_id();

        if (currentUserRoleId == null || currentUserRoleId != 3) {
            view.failureUpload("No tienes permisos para subir ofertas. Solo los administradores pueden realizar esta acción.");
            return;
        }

        model.uploadOffer(offer, this);
    }

    @Override
    public void onSuccess() {
        view.successUpload();

        Intent intent = new Intent(context, AllOffersView.class);
        context.startActivity(intent);
    }

    @Override
    public void onFailure(String err) {
        view.failureUpload(err);
    }
}