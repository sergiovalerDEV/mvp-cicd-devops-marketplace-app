package com.example.wallapop.product.edit_offer.model;

import com.example.wallapop.beans.Offer;
import com.example.wallapop.product.edit_offer.ContractEditOffer;
import com.example.wallapop.product.edit_offer.data.EditOfferData;
import com.example.wallapop.product.lst_admin_offers.data.DataAdminOffers;
import com.example.wallapop.utils.ApiService;
import com.example.wallapop.utils.RetrofitCliente;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditOfferModel implements ContractEditOffer.Model {
    private static final String BASE_URL = "http://54.225.144.72:3000/";

    @Override
    public void getOfferDetails(int offerId, final OnGetOfferDetailsListener listener) {
        ApiService apiService = RetrofitCliente.getClient(BASE_URL).create(ApiService.class);
        Call<DataAdminOffers> call = apiService.listAdminOffers();

        call.enqueue(new Callback<DataAdminOffers>() {
            @Override
            public void onResponse(Call<DataAdminOffers> call, Response<DataAdminOffers> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Offer> offers = response.body().getOffers();
                    for (Offer offer : offers) {
                        if (offer.getId() == offerId) {
                            listener.onGetOfferDetailsSuccess(offer);
                            return;
                        }
                    }
                    listener.onGetOfferDetailsFailure("No se encontró la oferta con ID: " + offerId);
                } else {
                    listener.onGetOfferDetailsFailure("Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<DataAdminOffers> call, Throwable t) {
                listener.onGetOfferDetailsFailure("Error de red: " + t.getMessage());
            }
        });
    }

    @Override
    public void editOffer(Offer offer, final OnEditOfferListener listener) {
        ApiService apiService = RetrofitCliente.getClient(BASE_URL).create(ApiService.class);
        Call<EditOfferData> call = apiService.editOffer(offer);

        call.enqueue(new Callback<EditOfferData>() {
            @Override
            public void onResponse(Call<EditOfferData> call, Response<EditOfferData> response) {
                if (response.isSuccessful() && response.body() != null) {
                    listener.onEditSuccess(response.body().getMessage());
                } else {
                    listener.onEditFailure("Error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<EditOfferData> call, Throwable t) {
                listener.onEditFailure("Error de red: " + t.getMessage());
            }
        });
    }
}