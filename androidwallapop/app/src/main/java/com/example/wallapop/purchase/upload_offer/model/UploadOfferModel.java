package com.example.wallapop.purchase.upload_offer.model;

import com.example.wallapop.beans.Offer;
import com.example.wallapop.purchase.upload_offer.ContractUploadOffer;
import com.example.wallapop.purchase.upload_offer.data.DataUploadOffer;
import com.example.wallapop.utils.ApiService;
import com.example.wallapop.utils.RetrofitCliente;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadOfferModel implements ContractUploadOffer.Model {
    private static final String IP_BASE = "http://54.225.144.72:3000/";

    @Override
    public void uploadOffer(Offer offer, OnUploadOfferListener listener) {
        ApiService apiService = RetrofitCliente.getClient(IP_BASE).create(ApiService.class);

        Call<DataUploadOffer> call = apiService.uploadOffer(offer);

        call.enqueue(new Callback<DataUploadOffer>() {
            @Override
            public void onResponse(Call<DataUploadOffer> call, Response<DataUploadOffer> response) {
                if (response.isSuccessful()) {
                    DataUploadOffer myData = response.body();
                    if (myData != null) {
                        listener.onSuccess();
                    } else {
                        listener.onFailure("Error: el cuerpo de la respuesta es nulo.");
                    }
                } else {
                    listener.onFailure("Error en la respuesta del servidor: " + response.message() + " (Código: " + response.code() + ")");
                }
            }

            @Override
            public void onFailure(Call<DataUploadOffer> call, Throwable t) {
                handleNetworkError(t, listener);
            }
        });
    }

    private void handleNetworkError(Throwable t, OnUploadOfferListener listener) {
        listener.onFailure("Error de red al subir la oferta: " + t.getMessage());
    }
}